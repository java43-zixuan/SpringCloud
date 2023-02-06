package com.example.userservice2.test22;

import java.util.*;
import java.util.stream.Collectors;

/**
 * List集合去重的6种方法
 */
public class ListDistinctExample {

    public static void main(String[] args) {
        List<Integer> arrayList = Arrays.asList(1,1,2,1,3,5,4,2,9);
        List<Integer> list = new ArrayList(arrayList);
        System.out.println("原集合："+list);
        /**
         * 第一种
         * 优点：理解起来比较简单，并且最终得到的集合也是有序的，这里的有序指的是新集合的排列顺序和原集合的顺序是一致的；
         * 缺点: 实现代码有点多，不够简洁优雅。
         */
        // 新集合
        List<Integer> newList = new ArrayList<>(list.size());
        list.forEach(i -> {
            if (!newList.contains(i)) { // 如果新集合中不存在则插入
                newList.add(i);
            }
        });
        System.out.println("第一种去重集合:" + newList);


        /**
         * 第二种
         * 此方法的实现比上一种方法的实现代码要少一些，并且不需要新建集合，
         * 但此方法得到的新集合是无序的，也就是新集合的排列顺序和原集合不一致，因此也不是最优的解决方案。
         */
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            // 获取循环的值
            Integer item = iterator.next();
            // 如果存在两个相同的值
            if (list.indexOf(item) != list.lastIndexOf(item)) {
                // 移除最后那个相同的值
                iterator.remove();
            }
        }
        System.out.println("第二种去重集合:" + list);


        /**
         * 第三种
         * 此方法的实现代码较为简洁，
         * 但缺点是 HashSet 会自动排序，这样新集合的数据排序就和原集合不一致了，如果对集合的顺序有要求，那么此方法也不能满足当前需求。
         */
        HashSet<Integer> set = new HashSet<>(list);
        System.out.println("第三种去重集合:" + set);


        /**
         * 第四种
         * 从上述代码和执行结果可以看出，LinkedHashSet 是到目前为止，实现比较简单，
         * 且最终生成的新集合与原集合顺序保持一致的实现方法，是我们可以考虑使用的一种去重方法。
         */
        LinkedHashSet<Integer> set2 = new LinkedHashSet<>(list);
        System.out.println("第四种去重集合:" + set2);


        /**
         * 第五种
         * TreeSet 虽然实现起来也比较简单，但它有着和 HashSet 一样的问题，会自动排序，因此也不能满足我们的需求。
         */
        TreeSet<Integer> set3 = new TreeSet<>(list);
        System.out.println("第五种去重集合:" + set3);

        /**
         * 第六种
         * Stream 实现去重功能和其他方法不同的是，它不用新创建集合，使用自身接收一个去重的结果就可以了，并且实现代码也很简洁，
         * 并且去重后的集合顺序也和原集合的顺序保持一致，是我们最优先考虑的去重方法。
         */
        list = list.stream().distinct().collect(Collectors.toList());
        System.out.println("第六种去重集合:" + list);
    }
}
