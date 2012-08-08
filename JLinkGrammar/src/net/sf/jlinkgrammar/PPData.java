package net.sf.jlinkgrammar;

/**
 * TODO add javadoc
 * 
 */
public class PPData {
	int N_domains;
	final ListOfLinks[] word_links = new ListOfLinks[GlobalBean.MAX_SENTENCE];
	ListOfLinks links_to_ignore;
	final Domain[] domain_array = new Domain[GlobalBean.MAX_LINKS]; /*
															 * the domains,
															 * sorted by size
															 */
	int length; /* length of current sentence */

}
