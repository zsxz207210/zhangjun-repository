package invertedIndex;

import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class PEKE_test {
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //生成双线性映射
        Pairing pairing = PairingFactory.getPairing("a.properties");

        PEKS peks = new PEKS();
        PEKS peks2 = new PEKS();
        //PEKS 初始化
        peks.Setup(pairing);
        peks2.Setup(pairing);
        String w0 = "2024-8-15";
        String w1 = "abcd";
        String w2 = "abc";
        String w = "大肠杆菌";
        //加密
        PEKS.C c = null;
        PEKS.C c1 = null;
        c = peks.Enc(peks.pk, w);
        c1 = peks2.Enc(peks.pk, w);
        System.out.println(c);
        System.out.println(c1);

        //生成陷门
        PEKS.TD td = null;
        PEKS.TD td1 = null;
        td = peks.TdGen(peks.pk, peks.sk, w);
        td1 = peks2.TdGen(peks.pk, peks.sk, w);
        System.out.println(td);
        System.out.println(td1);


        //搜索
        boolean res = peks.Test(peks.pk, td, c);
        boolean res1 = peks.Test(peks.pk, td1, c1);

        //搜索结果测试
        System.out.println(res);
        System.out.println(res1);

    }
}
