package net.sf.jlinkgrammar;

/*
 * Parser.java
 *
 * Created on October 20, 2006, 3:02 PM
 *
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * This class is meant to be a bean type interface to link grammar. All the
 * options are preset and specific actions have to be taken to override them.
 *
 * @author johnryan
 */
public class Parser {

    private static Dictionary dict;
    private static Sentence sent;
    private static String dictionary_file = null;
    private static String post_process_knowledge_file = null;
    private static String constituent_knowledge_file = null;
    private static String affix_file = null;
    private static boolean pp_on = true;
    private static boolean af_on = true;
    private static boolean cons_on = true;
    private static int num_linkages;
    private static StringBuffer input_string = new StringBuffer();
    private static int label = GlobalBean.NOT_LABEL;
    private static ParseOptions opts;

    /**
     * Creates a new instance of Parser
     */
    public Parser() {
        String[] args = new String[1];

        args[0] = new String("parseit");
        initializeVars(args);
    }

    public static void initializeVars(String arg[]) {

        int i = 0;
        if (arg.length > 1 && (arg[0].charAt(0) != '-')) {
            /* the dictionary is the first argument if it doesn't begin with "-" */
            dictionary_file = arg[0];
            i++;
        }
        opts = new ParseOptions();
        GlobalBean.opts = opts;

        opts.setMaxSentenceLength(70);
        opts.setLinkageLimit(1000);
        opts.setShortLength(10);

        for (i = 0; i < arg.length; i++) {
            if (arg[i].charAt(0) == '-') {
                if (arg[i].equals("-pp")) {
                    if ((post_process_knowledge_file != null) || (i + 1 == arg.length))
                        printUsage();
                    post_process_knowledge_file = arg[i + 1];
                    i++;
                } else if (arg[i].equals("-c")) {
                    if ((constituent_knowledge_file != null) || (i + 1 == arg.length))
                        printUsage();
                    constituent_knowledge_file = arg[i + 1];
                    i++;
                } else if (arg[i].equals("-a")) {
                    if ((affix_file != null) || (i + 1 == arg.length))
                        printUsage();
                    affix_file = arg[i + 1];
                    i++;
                } else if (arg[i].equals("-ppoff")) {
                    pp_on = false;
                } else if (arg[i].equals("-coff")) {
                    cons_on = false;
                } else if (arg[i].equals("-aoff")) {
                    af_on = false;
                } else if (arg[i].equals("-batch")) {
                    if ((opts.input != System.in) || (i + 1 == arg.length))
                        printUsage();
                    try {
                        opts.input = new FileInputStream(arg[i + 1]);
                    } catch (IOException ex) {
                    }
                    i++;

                } else if (arg[i].equals("-out")) {
                    if ((opts.out != System.out) || (i + 1 == arg.length))
                        printUsage();
                    try {
                        opts.out = new PrintStream(new FileOutputStream(
                                arg[i + 1]));
                    } catch (IOException ex) {
                        // TODO - Do something
                    }
                    i++;
                } else if (arg[i].charAt(1) == '!') {
                } else {
                    printUsage();
                }
            } else {
            }
        }

        if (!pp_on && post_process_knowledge_file != null)
            printUsage();

        if (dictionary_file == null) {
            // dictionary_file = defaultDataDir + "/link/4.0.dict";
            dictionary_file = "4.0.dict";
            System.err.println("No dictionary file specified.  Using " + dictionary_file + ".");
        }

        if (af_on && affix_file == null) {
            // affix_file = defaultDataDir + "/link/4.0.affix";
            affix_file = "4.0.affix";
            System.err.println("No affix file specified.  Using " + affix_file + ".");
        }

        if (pp_on && post_process_knowledge_file == null) {
            // post_process_knowledge_file = defaultDataDir +
            // "/link/4.0.knowledge";
            post_process_knowledge_file = "4.0.knowledge";
            System.err.println("No post process knowledge file specified.  Using " + post_process_knowledge_file + ".");
        }

        if (cons_on && constituent_knowledge_file == null) {
            // constituent_knowledge_file = defaultDataDir +
            // "/link/4.0.constituent-knowledge";
            constituent_knowledge_file = "4.0.constituent-knowledge";
            System.err.println("No constituent knowledge file specified.  Using " + constituent_knowledge_file + ".");
        }

        try {
            dict = new Dictionary(opts, dictionary_file, post_process_knowledge_file, constituent_knowledge_file, affix_file);
        } catch (IOException ex) {
        }

        /* process the command line like commands */
        for (i = 1; i < arg.length; i++) {
            if (!arg[i].equals("-pp") && !arg[i].equals("-c") && !arg[i].equals("-a")) {
                i++;
            } else if (arg[i].charAt(0) == '-' && !arg[i].equals("-ppoff") && !arg[i].equals("-coff") && !arg[i].equals("-aoff")) {
                opts.issueSpecialCommand(arg[i].substring(1), dict);
            }
        }

    }

    public static void doIt(String arg[]) throws IOException {

        initializeVars(arg);

        /*
           * This is the standard command line parser reading from the standard
           * input and displaying on the standard output.
           */
        while (GlobalBean.fgetInputString(input_string, opts.input, opts.out, opts)) {
            if (input_string.length() == 0) {
                continue;
            }
            if (input_string.equals("quit\n") || input_string.equals("exit\n"))
                break;
            if (GlobalBean.specialCommand(input_string, dict))
                continue;
            if (opts.getEchoOn()) {
                opts.out.println(input_string);
            }

            if (opts.getBatchMode()) {
                label = GlobalBean.stripOffLabel(input_string);
            }

            sent = new Sentence(input_string.toString(), dict, opts);

            if (sent.sentenceLength() > opts.getMaxSentenceLength()) {
                if (opts.verbosity > 0) {
                    opts.out.println("Sentence length ("
                            + sent.sentenceLength()
                            + " words) exceeds maximum allowable ("
                            + opts.getMaxSentenceLength()
                            + " words)");
                }
                continue;
            }

            /* First parse with cost 0 or 1 and no null links */
            opts.setDisjunctCost(2);
            opts.setMinNullCount(0);
            opts.setMaxNullCount(0);
            opts.resetResources();
            num_linkages = sent.sentenceParse(opts);

            // Now parse with null links
            // Even in Batch mode
            if (num_linkages == 0) {
                if (opts.verbosity > 0)
                    opts.out.println("No complete linkages found.");
                if (opts.getAllowNull()) {
                    opts.setMinNullCount(1);
                    opts.setMaxNullCount(sent.sentenceLength());
                    num_linkages = sent.sentenceParse(opts);
                }
            }

            //@OUTPUT
            //opts.printTotalTime();
            if (opts.getBatchMode()) {
                GlobalBean.batchProcessSomeLinkages(label, sent, opts);
            } else {
                GlobalBean.processSomeLinkages(sent, opts);
            }

        }

        if (opts.getBatchMode()) {
            opts.printTime("Total");
            opts.out.println("" + GlobalBean.batch_errors + " error" + ((GlobalBean.batch_errors == 1) ? "" : "s") + ".");
        }
    }

    /**
     * Instead of printing a link diagram print an XML tree
     *
     * @param sent the sentence to print.
     */
    public void printWordsLabelsAndLinks(Sentence sent) {
        CNode root, current, next, previous;
        Linkage linkage;
        int numLinkages;
        int num_to_query;
        int i;

        if (sent.numLinkagesFound() > 0) {
            // We have to walk all the linakges throwing away the bad ones.
            num_to_query = Math.min(
                    sent.numLinkagesPostProcess(), 1000);

            for (i = 0; i < num_to_query; ++i) {

                if ((sent.numViolations(i) > 0)
                        && (!opts.getDisplayBad())) {
                    continue;
                }

                // O.K. we have our fisrt valid linkage. Do we want to print
                // them all? No just one.
                // TODO - optimize this somehow
                linkage = new Linkage(i, sent, opts);
                // linkage = new Linkage(0, sent, opts);
                int j, mode, first_sublinkage;

                // In effect we are saying display sublinkages
                linkage.linkage_compute_union();
                numLinkages = linkage.linkage_get_num_sublinkages();
                first_sublinkage = numLinkages - 1;

                for (j = first_sublinkage; j < numLinkages; ++j) {
                    linkage.linkage_set_current_sublinkage(j);
                    root = linkage.linkage_constituent_tree();
                    // Now we can walk the linkage and print the structure
                    current = root;
                    int w = 0;
                    do {
                        opts.out.println(linkage.word[w++].toString());
                        displayCNode(current);
                    } while (current.next != null);
                    // string = linkage_print_diagram();
                    // opts.out.println(string);
                }

            }
        }
    }

    public void displayCNode(CNode n) {
        opts.out.println(n.toString());
    }

    /*
      * void print_words_with_prep_phrases_marked(CNode *n) { CNode * m; static
      * char * spacer=" ";
      *
      * if (n == NULL) return; if (strcmp(n->label, "PP")==0) { printf("%s[",
      * spacer); spacer=""; } for (m=n->child; m!=NULL; m=m->next) { if (m->child
      * == NULL) { printf("%s%s", spacer, m->label); spacer=" "; } else {
      * print_words_with_prep_phrases_marked(m); } } if (strcmp(n->label,
      * "PP")==0) { printf("]"); } }
      *
      * int main() {
      *
      * Dictionary dict; Parse_Options opts; Sentence sent; Linkage linkage;
      * CNode * cn; char * string; char * input_string =
      * "This is a test of the constituent code in the API.";
      *
      * opts = parse_options_create(); dict = dictionary_create("4.0.dict",
      * "4.0.knowledge", "4.0.constituent-knowledge", "4.0.affix");
      *
      * sent = sentence_create(input_string, dict); if (sentenceParse(sent,
      * opts)) { linkage = linkage_create(0, sent, opts); printf("%s", string =
      * linkage_print_diagram(linkage)); string_delete(string); cn =
      * linkage_constituent_tree(linkage);
      * print_words_with_prep_phrases_marked(cn);
      * linkage_free_constituent_tree(cn); fprintf(stdout, "\n\n");
      * linkage_delete(linkage); } sentence_delete(sent);
      *
      * dictionary_delete(dict); parse_options_delete(opts); return 0; }
      */

    static void printUsage() {
        System.err.println("Usage: <class name> [dict_file] [-pp PPKnowledge_file]\n"
                + "          [-c constituent_knowledge_file] [-a affix_file]\n"
                + "          [-ppoff] [-coff] [-aoff] [-batch] [-<special \"!\" command>]");
        System.exit(1);
    }
}
