package net.sf.jlinkgrammar;

import java.util.Random;

/**
 * TODO add javadoc
 * 
 */
public class MyRandom {
	private static final ThreadLocal<Integer[]> random_state = new ThreadLocal<Integer[]>();
	private static ThreadLocal<Integer> random_count = new ThreadLocal<Integer>();
	private static ThreadLocal<Boolean> random_inited = new ThreadLocal<Boolean>();
    static{
        random_count.set(0);
        random_inited.set(false);
        random_state.set(new Integer[2]);
    }

	static int step_generator(int d) {
		/* no overflow should occur, so this is machine independent */
		random_state.get()[0] = ((random_state.get()[0] * 3) + d + 104729) % 179424673;
		random_state.get()[1] = ((random_state.get()[1] * 7) + d + 48611) % 86028121;
		return random_state.get()[0] + random_state.get()[1];
	}

	static void my_random_initialize(int seed) {
		if (random_inited.get()) {
			throw new RuntimeException("Random number generator not finalized.");
		}

		seed = (seed < 0) ? -seed : seed;
		seed = seed % (1 << 30);

		random_state.get()[0] = seed % 3;
		random_state.get()[1] = seed % 5;
		random_count.set(seed);
		random_inited.set(true);
	}

	static void my_random_finalize() {
		if (!random_inited.get()) {
			throw new RuntimeException(
					"Random number generator not initialized.");
		}
		random_inited.set(false);
	}

	static int my_random() {
		random_count.set(random_count.get()+1);
		return step_generator(random_count.get());
	}

	static int randtable[];

	/* There is a legitimate question of whether having the hash function */
	/* depend on a large array is a good idea. It might not be fastest on */
	/* a machine that depends on caching for its efficiency. On the other */
	/* hand, Phong Vo's hash (and probably other linear-congruential) is */
	/* pretty bad. So, mine is a "competitive" hash function -- you can't */
	/* make it perform horribly. */

	static final int[] table = { 1932757444, 1812851329, 786279284, 481968937,
			1699140529, 368211428, 1619701602, 850790193, 1642638206,
			810407642, 199959575, 1030935169, 237895031, 590249699, 1997930855,
			53546138, 668396581, 1839177873, 638216208, 1670124393, 332713197,
			1035074448, 1985900155, 725370578, 1302741285, 134650351,
			638160306, 565314516, 1889387775, 1286302809, 598279984, 371398014,
			1765157118, 627747827, 2093973672, 1774683490, 1234867862,
			900890657, 282741182, 1140457973, 426936334, 993029438, 714291992,
			22021730, 375146338, 397206613, 2088659870, 125417311, 217963178,
			957649779, 1581056540, 1714510321, 1894596297, 1149077397,
			762000468, 989767529, 410414644, 1299598477, 1277489890,
			1122630357, 1953573275, 608438773, 1068191945, 1600570223,
			1112997517, 1753251473, 1880702872, 471094094, 214207675,
			1806439037, 1060615205, 1306229975, 1236269356, 1695691943,
			2066384122, 883661545, 1225359088, 582981237, 2039791837,
			1435771371, 164938417, 1742053748, 216310576, 1043163664,
			568809869, 201247637, 970822283, 1612621669, 236367783, 1274419026,
			367885892, 1693403629, 852443020, 816808751, 321312438, 1274937748,
			388295916, 731047, 2046295136, 1471956440, 623256169, 457640173,
			122892439, 789076669, 2011453414, 690799506, 2095546795, 945216095,
			2062904267, 449336492, 1480337815, 987739927, 1286292081,
			1798092960, 1159596896, 91917480, 2076486344, 680487839, 639315448,
			1468002708, 801264185, 376978745, 794027396, 1870199738, 762286927,
			1062023608, 1743588095, 1441296217, 1035736811, 442200340,
			1090177109, 1341873130, 1492862473, 525314313, 612815230,
			158954118, 1197446767, 115064511, 954594382, 26608502, 1783314117,
			1717350797, 1887692186, 2117048250, 1009117709, 1863314461,
			1282712932, 48430620, 867180337, 1552140448, 1027695159, 525780082,
			623319876, 739795051, 358805459, 491619364, 1527855986, 316738563,
			1321847884, 986947385, 1696971422, 995276576, 187026076, 344305712,
			1848677875, 1144774977, 483156009, 1549440309, 2121412304,
			1999380419, 497595803, 1879369164, 594896748, 469309131,
			1078200407, 1607496591, 1690138114, 1635863433, 1335691963,
			1137379732, 1389004786, 665198874, 1794263977, 1056814547,
			279316205, 1050395215, 1868800966, 882242146, 963222947,
			1467235005, 1842427299, 728620328, 951036741, 2009491792,
			266866641, 716331490, 2007833914, 2125572886, 847473227, 115502867,
			1915719833, 1354288800, 1767906618, 636684660, 166350996,
			1315982380, 1138375704, 1655217434, 438266004, 833449289,
			763494083, 1317211973, 779120379, 1228248282, 1223538804,
			909906305, 1715750230, 204314278, 637389875, 1991601665,
			1770611394, 1766650180, 1929458978, 1076621302, 1509878338,
			1328086592, 2136182943, 1603467395, 336273276, 1711542349,
			40826493, 1607189467, 364023175, 1373769304, 802456103, 117498232,
			1172073448, 1140654464, 735972418, 1272174737, 1695777339,
			939928547, 1123246298, 228259255, 1624554060, 1585351595,
			1790804095, 1762546412, 1742730951, 214231452, 1578390544,
			1791917801, 461576679, 2067990811, 1307470276, 1447253428 };

	static void init_randtable() {
		int i;
		Random r = new Random(10);

		randtable = new int[GlobalBean.RTSIZE];
		for (i = 0; i < GlobalBean.RTSIZE; i++) {
			randtable[i] = table[i];
			// randtable[i] = r.nextInt();
		}
	}

	static int next_power_of_two_up(int i) {
		/* Returns the smallest power of two that is at least i and at least 1 */
		int j = 1;
		while (j < i)
			j = j << 1;
		return j;
	}

}
