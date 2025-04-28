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
//                //如果有空值，直接返回0
////                c1 = c1.toLowerCase();//将term的值转成小写
////                c2 = c2.toLowerCase();
//                if (c1 == null || c2 == null)
//                    return 0;
//                if (c1.equals(c2)) {
//                    return 0; // 密文相等
//                } else {
//                    // 密文不相等，可以定义自己的比较逻辑
//                   return 1;
//                }
////                return String.valueOf(c1).compareTo(String.valueOf(c1));//然后进行比较
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
                    return 0; // 密文相等
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
            System.out.println("添加成功");
        } else {
            if (!fId.postingList.contains(detectNumber)) {
                fId.addFreq();
                fId.adddocId(detectNumber);
                System.out.println("更新成功");
            }
        }
    }

//    public void DB(PEKS peks,PEKS.TD t1,PEKS.TD t2) {
//        int i,j;
//        FreqAndId fid1 = dir.get(t1);//根据term值获取map中对应的value值，返回为一个Object
//        FreqAndId fid2 = dir.get(t2);//
//
//        List<String> l1 = fid1.freq > fid2.freq //较长的List命名为L1
//                ? fid1.postingList : fid2.postingList;
//        List<String> l2 = fid1.freq < fid2.freq //较短的List命名为L2
//                ? fid1.postingList : fid2.postingList;
//        List<String> list = new ArrayList<String>();//保存合并的结果
//
//        int len1 = l1.size(); //object的freq属性代表着postingList的长度
//        int len2 = l2.size();
//
//        for(i = 0,j = 0; i<len1 && j<len2;) {//直到遍历完其中一个postingList
//
//            while(l1.get(i) < l2.get(j)) {//找到l1第一个大于等于l2当前元素的位置
//                i++;
//            }
//            int id1 = l1.get(i);
//            int id2 = l2.get(j);
//            //System.out.println("i = "+l1.get(i)+"  j = "+l2.get(j));
//            if( id1 == id2 ) {
//                list.add(id2);//相等则加入合并列表
//                i++;                    //L1，L2均取下一个元素
//                j++;
//                //System.out.println("i = "+l1.get(i)+"  j = "+l2.get(j));
//            } else if( id1 > id2 ){
//                j++;//大于则找到L2下一个比L1当前元素大于等于的元素
//            }
//        }
//
//        System.out.print("Intersection: ");
//        for (int tmp : list) {    //输出结果
//            System.out.print("-->"+tmp);
//        }
//    }

    public Boolean single( BswabePub pub,BswabeToken token,String time) {
        ArrayList<Integer> list = new ArrayList<>();
        System.out.println("---开始查找---");
        Boolean res = false;

        long s = System.currentTimeMillis();// 开始时间
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
        System.out.println("---结束查找---");
        long e = System.currentTimeMillis();// 结束时间
        System.out.println("查询执行时长:"+(e - s)+"ms");//执行时间
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
        //模拟用户传来的数据
        riskFactor risk = new riskFactor("诺如病毒", "2024-10-7", "20241007-detect001-ZJHZ", "xxxx", "xxxx");
        //根据用户传的数据，找出两个关键词索引
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


        final String[] file1 = {"\"D:\\检测报告1.pdf\""};
        final String[] file2 = {"\"D:\\检测报告2.pdf\""};
        final String word1 = type;
        final String word2 = "大肠杆菌";

        //关键词对应的文件
        final Index index1 = new Index(word1, file1);
        final Index index2 = new Index(word2, file2);
        Index index3 = null;
        //参数定义
        BswabePub pub = new BswabePub();
        BswabeMsk msk = new BswabeMsk();
        BswabePrv prv;//A private key
        BswabeToken token1;//token
        BswabeToken token2;//token
        BswabeCph cph1;//public BswabePolicy p
        BswabeCph cph2;//public BswabePolicy p
        boolean result1 = false;
        boolean result2 = false;

        //参数初始化
        Bswabe.setup(u, pub, msk);
        //计算匹配执行时间
        long stime = System.currentTimeMillis();// 开始时间
        cph1 = Bswabe.enc(u, pub, policy, index1);
        long etime = System.currentTimeMillis();// 结束时间
        System.out.println("加密生成时长:" + (etime - stime) + "ms");//执行时间
        cph2 = Bswabe.enc(u, pub, policy, index2);
        System.out.println("cph1：" + cph1);
        System.out.println("cph2：" + cph2);

        prv = Bswabe.keygen(u, pub, msk, attrs);

        long start = System.currentTimeMillis();// 开始时间
        token1 = Bswabe.tokgen(prv, pub, "诺如病毒");
        long end = System.currentTimeMillis();// 开始时间
        System.out.println("陷门生成时长:" + (end - start) + "ms");//执行时间
        token2 = Bswabe.tokgen(prv, pub, "大肠杆菌");

        System.out.println("token1：" + token1);
        System.out.println("token2：" + token2);

        long s = System.currentTimeMillis();
        result1 = Bswabe.search(pub, token1, cph1);
        long e = System.currentTimeMillis();
        System.out.println("匹配执行时长:" + (e - s) + "ms");//执行时间
        result2 = Bswabe.search(pub, token2, cph2);
        System.out.println(result1);
        System.out.println(result2);


        System.out.println("=========构建索引======");
        //创建索引实例
        IndexForABSE InvertIndex= new IndexForABSE();

        Random random = new Random(); // 创建 Random 类的实例

        String batchNumber = risk.getBatchNumber();

        System.out.println("添加200条数据");
        BswabeCph c0 = null;
//        for (int i = 0; i < 200; i++) {
//            int randomInt = random.nextInt(); // 生成一个随机整数
//            String word = randomInt+"";
//            final String[] file3 = {"\"D:\\检测报告2.pdf\""};
//            index3 = new Index(word, file3);
//            c0 = Bswabe.enc(u, pub, policy, index3);
//            System.out.println(c0);
//            InvertIndex.Insert(c0, randomInt+""); // 使用生成的随机整数作为文档编号
//        }
        int count = 20241001;
        for (int i = 0; i < 140; i++) {
            int randomInt = random.nextInt(); // 生成一个随机整数
            String word = randomInt+"";
            final String[] file3 = {"\"D:\\检测报告2.pdf\""};
            index3 = new Index(word, file3);
            c0 = Bswabe.enc(u, pub, policy, index3);
            InvertIndex.Insert(c0, 20241001+""); // 使用生成的随机整数作为文档编号
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
