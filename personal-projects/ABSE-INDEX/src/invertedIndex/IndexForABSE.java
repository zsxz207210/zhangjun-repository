package invertedIndex;


import index.Index;
import pojo.riskFactor;
import scheme.*;

import java.util.*;

import static jdk.nashorn.internal.objects.NativeString.substring;

public class IndexForABSE {
    final static boolean DEBUG = true;
    class FreqAndId{
        private int freq;
        private List<String> postingList;

        public FreqAndId() {
            // TODO Auto-generated constructor stub
            freq = 0;
            postingList = new ArrayList<String>();
        }

        private void addFreq() {
            freq ++;
        }

        private void adddocId(String id) {
            postingList.add(id);
            postingList.sort(null);
        }
    }

    //public Map<PEKS.C,FreqAndId> dir;
    public Map<Object,FreqAndId> dir;
    static Formatter formatter = new Formatter(System.out);

//    public BoolRetrival() {
//        // TODO Auto-generated constructor stub
//        dir = new TreeMap<PEKS.C, FreqAndId>(new Comparator<PEKS.C>(){
//            public int compare(PEKS.C c1, PEKS.C c2) {
//                //����п�ֵ��ֱ�ӷ���0
////                c1 = c1.toLowerCase();//��term��ֵת��Сд
////                c2 = c2.toLowerCase();
//                if (c1 == null || c2 == null)
//                    return 0;
//                if (c1.equals(c2)) {
//                    return 0; // �������
//                } else {
//                    // ���Ĳ���ȣ����Զ����Լ��ıȽ��߼�
//                   return 1;
//                }
////                return String.valueOf(c1).compareTo(String.valueOf(c1));//Ȼ����бȽ�
//            }
//        });
//    }

    public IndexForABSE() {
        // TODO Auto-generated constructor stub
        dir = new TreeMap<Object, FreqAndId>(new Comparator<Object>(){
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 == null || o2 == null)
                    return 0;
                if (o1.equals(o2)) {
                    return 0; // �������
                }else {
                    return 1;
                }
            }

        });
    }

    public void Insert(Object key, String detectNumber) {
        FreqAndId fId = dir.get(key);
        if (fId == null) {
            fId = new FreqAndId();
            fId.addFreq();
            fId.adddocId(detectNumber);
            dir.put(key, fId);
            System.out.println("��ӳɹ�");
        } else {
            if (!fId.postingList.contains(detectNumber)) {
                fId.addFreq();
                fId.adddocId(detectNumber);
                System.out.println("���³ɹ�");
            }
        }
    }

//    public void DB(PEKS peks,PEKS.TD t1,PEKS.TD t2) {
//        int i,j;
//        FreqAndId fid1 = dir.get(t1);//����termֵ��ȡmap�ж�Ӧ��valueֵ������Ϊһ��Object
//        FreqAndId fid2 = dir.get(t2);//
//
//        List<String> l1 = fid1.freq > fid2.freq //�ϳ���List����ΪL1
//                ? fid1.postingList : fid2.postingList;
//        List<String> l2 = fid1.freq < fid2.freq //�϶̵�List����ΪL2
//                ? fid1.postingList : fid2.postingList;
//        List<String> list = new ArrayList<String>();//����ϲ��Ľ��
//
//        int len1 = l1.size(); //object��freq���Դ�����postingList�ĳ���
//        int len2 = l2.size();
//
//        for(i = 0,j = 0; i<len1 && j<len2;) {//ֱ������������һ��postingList
//
//            while(l1.get(i) < l2.get(j)) {//�ҵ�l1��һ�����ڵ���l2��ǰԪ�ص�λ��
//                i++;
//            }
//            int id1 = l1.get(i);
//            int id2 = l2.get(j);
//            //System.out.println("i = "+l1.get(i)+"  j = "+l2.get(j));
//            if( id1 == id2 ) {
//                list.add(id2);//��������ϲ��б�
//                i++;                    //L1��L2��ȡ��һ��Ԫ��
//                j++;
//                //System.out.println("i = "+l1.get(i)+"  j = "+l2.get(j));
//            } else if( id1 > id2 ){
//                j++;//�������ҵ�L2��һ����L1��ǰԪ�ش��ڵ��ڵ�Ԫ��
//            }
//        }
//
//        System.out.print("Intersection: ");
//        for (int tmp : list) {    //������
//            System.out.print("-->"+tmp);
//        }
//    }

    public Boolean single( BswabePub pub,BswabeToken token,String time) {
        ArrayList<Integer> list = new ArrayList<>();
        System.out.println("---��ʼ����---");
        Boolean res = false;

        long s = System.currentTimeMillis();// ��ʼʱ��
        for (Map.Entry<Object,FreqAndId> entry : dir.entrySet() ) {
            Object c = entry.getKey();
            res = Bswabe.search(pub, token, (BswabeCph) c);;
            if (res == true){
                FreqAndId freqAndId = entry.getValue();
                ArrayList<String> arrayList = (ArrayList<String>) freqAndId.postingList;
                int index = binarySearch(arrayList,time);
                System.out.println(arrayList.get(index));
                //System.out.println(freqAndId.postingList);
                break;
            }
        }
        System.out.println("---��������---");
        long e = System.currentTimeMillis();// ����ʱ��
        System.out.println("��ѯִ��ʱ��:"+(e - s)+"ms");//ִ��ʱ��
        return res;
    }

    int binarySearch(ArrayList<String> arr, String time)
    {
        int left = 0, right = arr.size() - 1;
        while (left <= right)
        {
            int mid = left + (right - left) / 2;
            String midValue = arr.get(mid);
            midValue = substring(midValue, 0,8);
            if (midValue.equals(time))
                return mid;
            if (midValue.compareTo(time) < 0)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }


    public static void main(String[] args) throws Exception {
        //ģ���û�����������
        riskFactor risk = new riskFactor("ŵ�粡��", "2024-10-7", "20241007-detect001-ZJHZ", "xxxx", "xxxx");
        //�����û��������ݣ��ҳ������ؼ�������
        String type = risk.getType();
        String time = risk.getTime();

        final String[] u = {"jianguanzhe", "yangzhi", "jiagong", "baozhuang", "wuliu", "cangchu", "xiaoshou"};
        final String[] attrs = {"jianguanzhe","wuliu"};
        final String[] policy = {"jianguanzhe","wuliu"};
//        final String []u={"ECNU","teacher", "doctor","master","bachelor","2016","2015","2014"};
//        //attributes of the user
//        final String []attrs = {"ECNU","teacher","2015","djksh"};
//        //attributes of the policy
//        final String []policy = {"ECNU","teacher","2015"};


        final String[] file1 = {"\"D:\\��ⱨ��1.pdf\""};
        final String[] file2 = {"\"D:\\��ⱨ��2.pdf\""};
        final String word1 = type;
        final String word2 = "�󳦸˾�";

        //�ؼ��ʶ�Ӧ���ļ�
        final Index index1 = new Index(word1, file1);
        final Index index2 = new Index(word2, file2);
        Index index3 = null;
        //��������
        BswabePub pub = new BswabePub();
        BswabeMsk msk = new BswabeMsk();
        BswabePrv prv;//A private key
        BswabeToken token1;//token
        BswabeToken token2;//token
        BswabeCph cph1;//public BswabePolicy p
        BswabeCph cph2;//public BswabePolicy p
        boolean result1 = false;
        boolean result2 = false;

        //������ʼ��
        Bswabe.setup(u, pub, msk);
        //����ƥ��ִ��ʱ��
        long stime = System.currentTimeMillis();// ��ʼʱ��
        cph1 = Bswabe.enc(u, pub, policy, index1);
        long etime = System.currentTimeMillis();// ����ʱ��
        System.out.println("��������ʱ��:" + (etime - stime) + "ms");//ִ��ʱ��
        cph2 = Bswabe.enc(u, pub, policy, index2);
        System.out.println("cph1��" + cph1);
        System.out.println("cph2��" + cph2);

        prv = Bswabe.keygen(u, pub, msk, attrs);

        long start = System.currentTimeMillis();// ��ʼʱ��
        token1 = Bswabe.tokgen(prv, pub, "ŵ�粡��");
        long end = System.currentTimeMillis();// ��ʼʱ��
        System.out.println("��������ʱ��:" + (end - start) + "ms");//ִ��ʱ��
        token2 = Bswabe.tokgen(prv, pub, "�󳦸˾�");

        System.out.println("token1��" + token1);
        System.out.println("token2��" + token2);

        long s = System.currentTimeMillis();
        result1 = Bswabe.search(pub, token1, cph1);
        long e = System.currentTimeMillis();
        System.out.println("ƥ��ִ��ʱ��:" + (e - s) + "ms");//ִ��ʱ��
        result2 = Bswabe.search(pub, token2, cph2);
        System.out.println(result1);
        System.out.println(result2);


        System.out.println("=========��������======");
        //��������ʵ��
        IndexForABSE InvertIndex= new IndexForABSE();

        Random random = new Random(); // ���� Random ���ʵ��

        String batchNumber = risk.getBatchNumber();

        System.out.println("���200������");
        BswabeCph c0 = null;
//        for (int i = 0; i < 200; i++) {
//            int randomInt = random.nextInt(); // ����һ���������
//            String word = randomInt+"";
//            final String[] file3 = {"\"D:\\��ⱨ��2.pdf\""};
//            index3 = new Index(word, file3);
//            c0 = Bswabe.enc(u, pub, policy, index3);
//            System.out.println(c0);
//            InvertIndex.Insert(c0, randomInt+""); // ʹ�����ɵ����������Ϊ�ĵ����
//        }
        int count = 20241001;
        for (int i = 0; i < 140; i++) {
            int randomInt = random.nextInt(); // ����һ���������
            String word = randomInt+"";
            final String[] file3 = {"\"D:\\��ⱨ��2.pdf\""};
            index3 = new Index(word, file3);
            c0 = Bswabe.enc(u, pub, policy, index3);
            InvertIndex.Insert(c0, 20241001+""); // ʹ�����ɵ����������Ϊ�ĵ����
            count++;
        }
        Boolean res = InvertIndex.single(pub, token1,"20241005");
        System.out.println(res);



        InvertIndex.Insert(cph1, batchNumber);
        InvertIndex.Insert(cph1, "20241002-detect01-SDQD");
        InvertIndex.Insert(cph1, "20241003-detect01-SDQD");
        InvertIndex.Insert(cph1, "20241004-detect01-SDQD");
        InvertIndex.Insert(cph1, "20241005-detect01-SDQD");
        InvertIndex.Insert(cph1, "20241006-detect01-SDQD");
        InvertIndex.Insert(cph1, "20241007-detect01-SDQD");
//        for (int i = 0; i < 10; i++) {
//            InvertIndex.Insert(cph1, batchNumber + (i+1));
//        }
        InvertIndex.Insert(cph2, batchNumber + 2);

//      System.out.println(InvertIndex.dir);
        FreqAndId freqAndId = InvertIndex.dir.get(cph1);
        FreqAndId freqAndId2 = InvertIndex.dir.get(cph2);
        System.out.println(freqAndId.postingList);
        System.out.println(freqAndId2.postingList);

        Boolean resault1 = InvertIndex.single(pub, token1,"20241005");
        //Boolean resault2 = InvertIndex.single(pub, token2);
        System.out.println(resault1);
        //System.out.println(resault2);
    }
}
