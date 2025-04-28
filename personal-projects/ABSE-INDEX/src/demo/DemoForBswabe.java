
package demo;
import index.Index;
import it.unisa.dia.gas.jpbc.Element;
import scheme.Bswabe;
import scheme.BswabeCph;
import scheme.BswabeMsk;
import scheme.BswabePrv;
import scheme.BswabePub;
import scheme.BswabeToken;

public class DemoForBswabe {
	final static boolean DEBUG = true;
    /*final static String inputfile = "F://dataEclipse/keyword.txt";
	final static String encfile = "F://dataEclipse/cipher.txt";
	final static String decfile = "F://dataEclipse/result.txt";//输出搜索结果：文件的id或者TRUE/false
*/

	//universal attribute set, any attribute is in u.
	final static String []u={"ECNU","teacher", "doctor","master","bachelor","2016","2015","2014"};
	//attributes of the user
	final static String []attrs = {"ECNU","teacher","2015","djksh","dsfsdsd","ECNU","teacher","2015","djksh","dsfsdsd","ECNU","teacher","2015","djksh","dsfsdsd","ECNU","teacher","2015","djksh","dsfsdsd","ECNU","teacher","2015","djksh","dsfsdsd","ECNU","teacher","2015","djksh","dsfsdsd","ECNU","teacher","2015","djksh","dsfsdsd",};
	//attributes of the policy
	final static String []policy = {"ECNU","teacher","2015","dacas","vfddfv"};
	final static String []file = {"1","3","5","6"};//包含关键字的文件标识
	final static Index index = new Index("123",file); //要加密的部分
	final static String word = "123";//要搜索的部分
	final static String word0 = "1234";


	public static void main(String[] args) throws Exception {
		BswabePub pub = new BswabePub();
		BswabeMsk msk = new BswabeMsk();
		BswabePrv prv;
		BswabeToken token;
		BswabeToken token0;
		BswabeCph cph;
		BswabeCph cph1;
		boolean result1 = false;
		boolean result2 = false;

		Bswabe.setup(u,pub, msk);

		long stime = System.currentTimeMillis();// 开始时间
		cph = Bswabe.enc(u,pub, policy, index);
		long etime = System.currentTimeMillis();// 结束时间
		System.out.println("加密生成时长:" + (etime - stime) + "ms");//执行时间

		cph1 = Bswabe.enc(u,pub, policy, index);
		System.out.println(cph);
		System.out.println(cph1);

		prv = Bswabe.keygen(u,pub, msk, attrs);

		long s = System.currentTimeMillis();// 开始时间
		token = Bswabe.tokgen(prv,pub,word);
		long e = System.currentTimeMillis();// 结束时间
		System.out.println("陷门:" + (e - s) + "ms");//执行时间

		token0 = Bswabe.tokgen(prv,pub,word);
		System.out.println(token);
		System.out.println(token0);

		long s1 = System.currentTimeMillis();// 开始时间
		result1 = Bswabe.search(pub, token, cph);
		long e1 = System.currentTimeMillis();// 结束时间
		System.out.println("匹配:" + (e1 - s1) + "ms");//执行时间
		result2 = Bswabe.search(pub, token0, cph);
		System.out.println(result1);
		System.out.println(result2);
		if (result1){
			String []fileReturned = index.file;
			for(int i=0;i<fileReturned.length;i++)
				System.out.print(fileReturned[i]+" ");
			System.out.println();
		} else
			System.err.println("There are no results!");


	}

	private static void println(Object o) {
		if (DEBUG)
			System.out.println(o);
	}
}
