package net.sf.jlinkgrammar;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Comparator;

/**
 * ParseOptions keeps a set of state variables regarding user choices. A parse
 * option is associated with each sentence and each linkage. It is important to
 * keep track of which parse option instance object is being referenced in any
 * given situation.
 * <p/>
 * Here's how the default values are initialized
 * <ul>
 * <li>verbosity = 5;
 * <li>linkage_limit = 100;
 * <li>disjunct_cost = GlobalBean.MAX_DISJUNCT_COST;
 * <li>min_null_count = 0;
 * <li>max_null_count = 0;
 * <li>null_block = 1;
 * <li>islands_ok = false;
 * <li>cost_model = new VDALCostModel();
 * <li>short_length = 6;
 * <li>all_short = false;
 * <li>twopass_length = 30;
 * <li>max_sentence_length = 70;
 * <li>resources = new Resources();
 * <li>display_short = true;
 * <li>display_word_subscripts = true;
 * <li>display_link_subscripts = true;
 * <li>display_walls = false;
 * <li>display_union = false;
 * <li>allow_null = true;
 * <li>echo_on = false;
 * <li>batch_mode = false;
 * <li>screen_width = 79;
 * <li>display_on = true;
 * <li>display_postscript = false;
 * <li>display_constituents = 0;
 * <li>display_bad = false;
 * <li>display_links = false;
 * <li>out = System.out;
 * <li>input = System.in;
 * </ul>
 */
public class ParseOptions {
    /**
     * Level of detail to give about the computation default 0
     */
    public int verbosity;
    /**
     * The maximum number of linkages processed default 10000
     *
     * @see Sentence#parse(int, ParseOptions)
     */
    public int linkage_limit;
    /**
     * Max disjunct cost to allow default 10000
     *
     * @see GlobalBean#MAX_DISJUNCT_COST
     */
    public int disjunct_cost;
    /**
     * The minimum number of null links to allow default 0
     *
     * @see Sentence#sentenceParse(ParseOptions)
     */
    public int min_null_count;
    /**
     * The maximum number of null links to allow
     *
     * @see Sentence#sentenceParse(ParseOptions) default 0
     */
    public int max_null_count;
    /**
     * consecutive blocks of this many words are considered as one null link
     * (default=1)
     *
     * @see Sentence#count(int, int, Connector, Connector, int, ParseOptions)
     */
    public int null_block;
    /**
     * If true, then linkages with islands (separate component of the link
     * graph) will be generated (default=false)
     *
     * @see Sentence#count(int, int, Connector, Connector, int, ParseOptions)
     */
    public boolean islands_ok;
    /**
     * min length for two-pass post processing
     */
    public int twopass_length;
    /**
     * max_sentence_length = 70 Where is this used? Can't find a reference to it
     * in the code!
     */
    public int max_sentence_length;
    /**
     * Links that are limited in length can be no longer than this. Default = 6
     */
    public int short_length;
    /**
     * If true, there can be no connectors that are exempt
     */
    public boolean all_short;
    /**
     * For sorting linkages in post_processing
     */
    public Comparator cost_model;
    /**
     * For deciding when to "abort" the parsing
     */
    public Resources resources;
    public boolean display_short;
    /**
     * as in "dog.n" as opposed to "dog"
     */
    public boolean display_word_subscripts;
    /**
     * as in "Ss" as opposed to "S"
     */
    public boolean display_link_subscripts;
    /**
     * set true to display walls default = true.
     */
    public boolean display_walls;
    /**
     * print squashed version of linkage with conjunction?
     */
    public boolean display_union;
    /**
     * true if we allow null links in parsing
     */
    public boolean allow_null;
    /**
     * true if we should echo the input sentence
     */
    public boolean echo_on;
    /**
     * if true, process sentences non-interactively
     */
    public boolean batch_mode;
    /**
     * width of screen for displaying linkages
     */
    public int screen_width;
    /**
     * if true, output graphical linkage diagram
     */
    public boolean display_on;
    /*
      * if true, output postscript linkage
      */
    public boolean display_postscript;
    /**
     * if true, output treebank-style constituent structure
     */
    public int display_constituents;
    /**
     * if true, bad linkages are displayed
     */
    public boolean display_bad;
    /**
     * if true, a list o' links is printed out
     */
    public boolean display_links;
    /**
     * Set the input reader
     */
    public InputStream input;
    /**
     * Set the output writer
     */
    public PrintStream out;

    /**
     * Here's where the values are initialized
     * <ul>
     * <li>verbosity = 5;
     * <li>linkage_limit = 100;
     * <li>disjunct_cost = GlobalBean.MAX_DISJUNCT_COST;
     * <li>min_null_count = 0;
     * <li>max_null_count = 0;
     * <li>null_block = 1;
     * <li>islands_ok = false;
     * <li>cost_model = new VDALCostModel();
     * <li>short_length = 6;
     * <li>all_short = false;
     * <li>twopass_length = 30;
     * <li>max_sentence_length = 70;
     * <li>resources = new Resources();
     * <li>display_short = true;
     * <li>display_word_subscripts = true;
     * <li>display_link_subscripts = true;
     * <li>display_walls = false;
     * <li>display_union = false;
     * <li>allow_null = true;
     * <li>echo_on = false;
     * <li>batch_mode = false;
     * <li>screen_width = 79;
     * <li>display_on = true;
     * <li>display_postscript = false;
     * <li>display_constituents = 0;
     * <li>display_bad = false;
     * <li>display_links = false;
     * <li>out = System.out;
     * <li>input = System.in;
     * </ul>
     */
    public ParseOptions() {

        // verbosity = 5;
        verbosity = 0;
        linkage_limit = 100;
        disjunct_cost = GlobalBean.MAX_DISJUNCT_COST;
        min_null_count = 0;
        max_null_count = 0;
        null_block = 1;
        islands_ok = false;
        cost_model = new VDALCostModel();
        short_length = 6;
        all_short = false;
        twopass_length = 30;
        max_sentence_length = 70;
        resources = new Resources();
        display_short = true;
        display_word_subscripts = true;
        display_link_subscripts = true;
        display_walls = false;
        display_union = false;
        allow_null = true;
        echo_on = false;
        batch_mode = false;
        screen_width = 79;
        display_on = true;
        display_postscript = false;
        display_constituents = 0;
        display_bad = false;
        display_links = false;
        out = System.out;
        input = System.in;
    }

    public void costModelType(ParseOptions opts, int cm) {
        switch (cm) {
            case GlobalBean.VDAL:
                cost_model = new VDALCostModel();
                break;
            default:
                throw new RuntimeException("Illegal cost model: " + cm);
        }
    }

    public void setVerbosity(int dummy) {
        verbosity = dummy;
    }

    public int getVerbosity() {
        return verbosity;
    }

    public void setLinkageLimit(int dummy) {
        linkage_limit = dummy;
    }

    public int getLinkageLimit() {
        return linkage_limit;
    }

    public void setDisjunctCost(int dummy) {
        disjunct_cost = dummy;
    }

    public int getDisjunctCost() {
        return disjunct_cost;
    }

    public void setMinNullCount(int val) {
        min_null_count = val;
    }

    public int getMinNullCount() {
        return min_null_count;
    }

    public void setMaxNullCount(int val) {
        max_null_count = val;
    }

    public int getMaxNullCount() {
        return max_null_count;
    }

    public void setNullBlock(int dummy) {
        null_block = dummy;
    }

    public int getNullBlock() {
        return null_block;
    }

    public void setIslandsOK(boolean dummy) {
        islands_ok = dummy;
    }

    public boolean getIslandsOK() {
        return islands_ok;
    }

    public void setShortLength(int short_length) {
        this.short_length = short_length;
    }

    public int getShortLength() {
        return short_length;
    }

    public void setAllShortConnectors(boolean val) {
        all_short = val;
    }

    boolean getAllShortConnectors() {
        return all_short;
    }

    public void setMaxSentenceLength(int dummy) {
        max_sentence_length = dummy;
    }

    public int getMaxSentenceLength() {
        return max_sentence_length;
    }

    public void setEchoOn(boolean dummy) {
        echo_on = dummy;
    }

    public boolean getEchoOn() {
        return echo_on;
    }

    public void setBatchMode(boolean dummy) {
        batch_mode = dummy;
    }

    public boolean getBatchMode() {
        return batch_mode;
    }

    public void setAllowNull(boolean dummy) {
        allow_null = dummy;
    }

    public boolean getAllowNull() {
        return allow_null;
    }

    public void setScreenWidth(int dummy) {
        screen_width = dummy;
    }

    public int getScreenWidth() {
        return screen_width;
    }

    public void setDisplayOn(boolean dummy) {
        display_on = dummy;
    }

    public boolean getDisplayOn() {
        return display_on;
    }

    public void setDisplayPostscript(boolean dummy) {
        display_postscript = dummy;
    }

    public boolean getDisplayPostscript() {
        return display_postscript;
    }

    public void setDisplayConstituents(ParseOptions opts,
                                       int dummy) {
        if ((dummy < 0) || (dummy > 2)) {
            System.err.println("Possible values for constituents: ");
            System.err
                    .println("   0 (no display) 1 (treebank style) or 2 (flat tree)");
            display_constituents = 0;
        } else
            display_constituents = dummy;
    }

    public int getDisplayConstituents() {
        return display_constituents;
    }

    public void setDisplayBad(boolean dummy) {
        display_bad = dummy;
    }

    public boolean getDisplayBad() {
        return display_bad;
    }

    public void setDisplayLinks(boolean dummy) {
        display_links = dummy;
    }

    public boolean getDisplayLinks() {
        return display_links;
    }

    public void setDisplayWalls(boolean dummy) {
        display_walls = dummy;
    }

    public boolean getDisplayWalls() {
        return display_walls;
    }

    public void setDisplayUnion(boolean dummy) {
        display_union = dummy;
    }

    public boolean getDisplayUnion() {
        return display_union;
    }

    public void resetResources() {
        resources.reset();
    }

    public void cleanUpString(StringBuffer s) {
        /* gets rid of all the white space in the string s. Changes s */
        int i = 0;
        while (i < s.length()) {
            if (Character.isWhitespace(s.charAt(i))) {
                s.deleteCharAt(i);
            } else {
                i++;
            }
        }
    }

    public void issueSpecialCommand(String line, Dictionary dict) {
        StringBuffer myline = new StringBuffer(line);
        String s, x, y;
        int i, count, j, k;
        Switch as[] = default_switches;

        cleanUpString(myline);

        s = myline.toString();
        j = k = -1;
        count = 0;
        for (i = 0; i < as.length; i++) {
            if (as[i].isboolean && as[i].string.startsWith(s)) {
                count++;
                j = i;
            }
        }
        for (i = 0; i < user_command.length; i++) {
            if (user_command[i].s.startsWith(s)) {
                count++;
                k = i;
            }
        }

        if (count > 1) {
            out.println("Ambiguous command.  Type \"!help\" or \"!variables\"");
            return;
        } else if (count == 1) {
            if (j >= 0) {
                int b = as[j].p.get() == 0 ? 1 : 0;
                as[j].p.set(b);
                out.println(as[j].description + " turned "
                        + (as[j].p.get() != 0 ? "on" : "off") + ".");
                return;
            } else {
                /* replace the abbreviated command by the full one */
                s = user_command[k].s;
            }
        }

        if (s.equals("variables")) {
            out.println(" Variable     Controls                                      Value");
            out.println(" --------     --------                                      -----");
            for (i = 0; i < as.length; i++) {
                out.print(" ");
                leftPrintString(as[i].string, "             ");
                leftPrintString(as[i].description,
                        "                                              ");
                if (as[i].isboolean) {
                    if (as[i].p.get() != 0)
                        out.print(" (On)");
                    else
                        out.print(" (Off)");
                } else {
                    out.print(as[i].p.get());
                }
                out.println();
            }
            out.println();
            out.print("Toggle a boolean variable as in \"!batch\"; ");
            out.println("set a variable as in \"!width=100\".");
            return;
        }
        if (s.equals("help")) {
            out.println("Special commands always begin with \"!\".  Command and variable names");
            out.println("can be abbreviated.  Here is a list of the commands:");
            for (i = 0; i < user_command.length; i++) {
                out.print(" !");
                leftPrintString(user_command[i].s, "                  ");
                leftPrintString(user_command[i].str,
                        "                                                    ");
                out.println();
            }
            out.println(" !!<string>         Print all the dictionary words matching <string>.");
            out.println("                    Also print the number of disjuncts of each.");
            out.println();
            out.println(" !<var>             Toggle the specified boolean variable.");
            out.println(" !<var>=<val>       Assign that value to that variable.");
            return;
        }

        if (s.charAt(0) == '!') {
            dict.dict_display_word_info(s.substring(1));
            return;
        }

        /* test here for an equation */
        int ix = s.indexOf('=');
        if (ix > 0) {
            x = s.substring(0, ix);
            y = s.substring(ix + 1);
            /* now x is the first word and y is the rest */
            if (isNumericalRHS(y)) {
                for (i = 0; i < as.length; i++) {
                    if (x.equals(as[i].string))
                        break;
                }
                if (as[i].string == null) {
                    out.println("There is no user variable called \"" + x
                            + "\".");
                } else {
                    try {
                        as[i].p.set(Integer.parseInt(y));
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Error parsing " + y);
                    }
                    out.println(x + " set to " + y);
                }
                return;
            }
        }
        out.println("I can't interpret \"" + myline
                + "\" as a command.  Try \"!help\".");
    }

    public interface Setter {
        void set(int value);

        int get();
    }

    abstract class BooleanSetter implements Setter {
        public void set(int value) {
            set(value != 0);
        }

        public int get() {
            return getBoolean() ? 1 : 0;
        }

        abstract void set(boolean value);

        abstract boolean getBoolean();
    }

    class Switch {
        String string;
        boolean isboolean;
        String description;
        Setter p;

        Switch(String string, boolean isboolean, String description, Setter p) {
            this.string = string;
            this.isboolean = isboolean;
            this.description = description;
            this.p = p;
        }
    }

    ;

    Switch default_switches[] = {
            new Switch("verbosity", false, "Level of detail in output",
                    new Setter() {
                        public void set(int value) {
                            verbosity = value;
                        }

                        public int get() {
                            return verbosity;
                        }
                    }),
            new Switch("limit", false, "The maximum linkages processed",
                    new Setter() {
                        public void set(int value) {
                            linkage_limit = value;
                        }

                        public int get() {
                            return linkage_limit;
                        }
                    }),
            new Switch("null-block", false,
                    "Size of blocks with null cost true", new Setter() {
                public void set(int value) {
                    null_block = value;
                }

                public int get() {
                    return null_block;
                }
            }),
            new Switch("islands-ok", true, "Use of null-linked islands",
                    new BooleanSetter() {
                        public void set(boolean value) {
                            islands_ok = value;
                        }

                        public boolean getBoolean() {
                            return islands_ok;
                        }
                    }),
            new Switch("short", false, "Max length of short links",
                    new Setter() {
                        public void set(int value) {
                            short_length = value;
                        }

                        public int get() {
                            return short_length;
                        }
                    }),
            new Switch("batch", true, "Batch mode", new BooleanSetter() {
                public void set(boolean value) {
                    batch_mode = value;
                }

                public boolean getBoolean() {
                    return batch_mode;
                }
            }),
            new Switch("null", true, "Null links", new BooleanSetter() {
                public void set(boolean value) {
                    allow_null = value;
                }

                public boolean getBoolean() {
                    return allow_null;
                }
            }),
            new Switch("width", false, "The width of the display",
                    new Setter() {
                        public void set(int value) {
                            screen_width = value;
                        }

                        public int get() {
                            return screen_width;
                        }
                    }),
            new Switch("echo", true, "Echoing of input sentence",
                    new BooleanSetter() {
                        public void set(boolean value) {
                            echo_on = value;
                        }

                        public boolean getBoolean() {
                            return echo_on;
                        }
                    }),
            new Switch("graphics", true, "Graphical display of linkage",
                    new BooleanSetter() {
                        public void set(boolean value) {
                            display_on = value;
                        }

                        public boolean getBoolean() {
                            return display_on;
                        }
                    }),
            new Switch("postscript", true, "Generate postscript output",
                    new BooleanSetter() {
                        public void set(boolean value) {
                            display_postscript = value;
                        }

                        public boolean getBoolean() {
                            return display_postscript;
                        }
                    }),
            new Switch("constituents", false, "Generate constituent output",
                    new Setter() {
                        public void set(int value) {
                            display_constituents = value;
                        }

                        public int get() {
                            return display_constituents;
                        }
                    }),
            new Switch("max-length", false, "Maximum sentence length",
                    new Setter() {
                        public void set(int value) {
                            max_sentence_length = value;
                        }

                        public int get() {
                            return max_sentence_length;
                        }
                    }),
            new Switch("bad", true, "Display of bad linkages",
                    new BooleanSetter() {
                        public void set(boolean value) {
                            display_bad = value;
                        }

                        public boolean getBoolean() {
                            return display_bad;
                        }
                    }),
            new Switch("links", true, "Showing of complete link data",
                    new BooleanSetter() {
                        public void set(boolean value) {
                            display_links = value;
                        }

                        public boolean getBoolean() {
                            return display_links;
                        }
                    }),
            new Switch("walls", true, "Showing of wall words",
                    new BooleanSetter() {
                        public void set(boolean value) {
                            display_walls = value;
                        }

                        public boolean getBoolean() {
                            return display_walls;
                        }
                    }),
            new Switch("union", true, "Showing of 'union' linkage",
                    new BooleanSetter() {
                        public void set(boolean value) {
                            display_union = value;
                        }

                        public boolean getBoolean() {
                            return display_union;
                        }
                    })};

    static class UserCommand {
        String s;
        String str;

        UserCommand(String s, String str) {
            this.s = s;
            this.str = str;
        }
    }

    static UserCommand user_command[] = {
            new UserCommand("variables",
                    "List user-settable variables and their functions"),
            new UserCommand("help", "List the commands and what they do")};

    void printTime(String s) {
        resources.printTime(this, s);
    }

    void printTotalTime() {
        resources.printTotalTime(this);
    }

    boolean isNumericalRHS(String s) {
        /*
           * return true if s points to a number: optional + or - followed by 1 or
           * more digits.
           */
        try {
            int i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void leftPrintString(String s, String t) {
        /*
           * prints s then prints the last |t|-|s| characters of t. if s is longer
           * than t, it truncates s.
           */
        int i, j, k;
        j = t.length();
        k = s.length();
        for (i = 0; i < j; i++) {
            if (i < k) {
                out.print(s.charAt(i));
            } else {
                out.print(t.charAt(i));
            }
        }
    }

    public void printExpression(Exp n) {
        ExpList el;
        int i;
        if (n == null) {
            out.print("null expression");
            return;
        }
        if (n.type == GlobalBean.CONNECTOR_type) {
            for (i = 0; i < n.cost; i++)
                out.print("[");
            out.print(n.string + n.dir);
            for (i = 0; i < n.cost; i++)
                out.print("]");
        } else {
            for (i = 0; i < n.cost; i++)
                out.print("[");
            if (n.cost == 0)
                out.print("(");
            for (el = n.l; el != null; el = el.next) {
                printExpression(el.e);
                if (el.next != null) {
                    if (n.type == GlobalBean.AND_type)
                        out.print(" & ");
                    if (n.type == GlobalBean.OR_type)
                        out.print(" | ");
                }
            }
            for (i = 0; i < n.cost; i++)
                out.print("]");
            if (n.cost == 0)
                out.print(")");
        }
    }

}
