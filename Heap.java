import java.util.Arrays;

public class Heap {
    //堆是一个完全二叉树，通常使用数组来表示[0,size)
    //大堆：父节点 > 子节点，根节点是 最大值
    //小堆：父节点 < 子节点，根节点是 最小值

    public static void shiftDown(int[] array, int size, int index) {
        //实现大堆的向下调整：父节点 => 子节点[O(logN)]

        //index 表示从出发调整的节点
        int parent = index;
        //根据父节点下标，先找到左子树下标
        //左子树下标 = 2*父节点下标 + 1;
        //右子树下标 = 2*父节点下标 + 2;
        //父节点下标 = （任一子树下标 - 1）/2;
        int child = 2 * parent + 1;
        while (child <size) {
            //循环表示：若 child 对应节点存在，进入循环
            //若 大于 size,表示当前parent已经是叶子节点了，没有子节点
            if (child + 1 < size && array[child + 1] > array[child]) {
                child = child + 1;
            }
            //child 指向左右子树中值较大的那个

            //将 child 位置的元素与 parent 位置元素比较大小
            //若不符合大堆要求，则交换位置
            if (array[child] > array[parent]) {
                int tmp = array[child];
                array[child] = array[parent];
                array[parent] = tmp;
            }else {
                break;
            }
            //下次循环之前，更新 parent, child
            parent = child;
            child = 2 * parent + 1;
        }
    }

    public static void creatHeap(int[] array, int size) {
        //建堆：O(N)
        //从后往前遍历，从最后一个非叶子节点的 父节点 出发，依次从当前位置进行向下调整
        //基于向下调整的方式建堆，必须从后往前遍历
        //基于向上调整的方式建堆，必须从前往后遍历

        //size - 1 得到的是最后一个元素的下标
        //再次 -1/2 计算其对应父节点下标
        for (int i = (size - 1 - 1) / 2; i >= 0; i--) {
            // i 为节点下标
            shiftDown(array, size, i);
        }
    }

    public static void main1(String[] args) {
        int[] array = {5, 7, 2, 9, 4, 6, 1, 8};
        creatHeap(array, array.length);
        System.out.println(Arrays.toString(array));
    }

    public static class MyPriorityQueue {
        //优先(级)队列：每次出队列的元素都是优先级最高的元素
        //普通队列：先进先出

        //实现优先队列核心数据结构就是堆
        //堆顶元素正是最大值（优先级最高的元素）
        //进行入队列时，把元素插入到数组末尾，然后向上调整
        //进行出队列时，把堆顶元素删除掉，并且进行向下调整
        private int[] array = new int[100];
        private int size = 0;//[0, size) 表示有效元素区间

        public void offer(int x) {
            //1.先把 x 放到数组元素的末尾
            array[size] = x;
            size++;
            //2.把 x 进行向上调整
            //第一个参数表示用来承载堆的数组
            //第二个参数表示数组中有效元素的个数
            //第三个参数表示向上调整的位置
            shiftUp(array, size, size - 1);
        }
        private void shiftUp(int[] array, int size, int index) {
            //实现大堆的向上调整：子节点 => 父节点[O(logN)]
            int child = index;
            int parent = (child - 1) / 2;
            //child = 0; 说明 child 已经是根节点了，根节点就没有父节点，则调整完毕
            while (child > 0) {
                if (array[parent] < array[child]) {
                    int tmp = array[parent];
                    array[parent] = array[child];
                    array[child] = tmp;
                }else {
                    break;
                }
                child = parent;
                parent = (child - 1) / 2;
            }
        }
        public Integer poll() {
            //出队列：删除首元素（根节点）

            if (size <= 0) {
                return null;
            }
            int ret = array[0];
            //1.把最后一个元素的值填入到 0 号元素上
            array[0] = array[size - 1];
            //2.删除最后一个元素
            size--;
            //3.从 0 下标开始进行向下调整
            shiftDown(array, size, 0);
            return ret;
        }
        private void shiftDown(int[] array, int size, int index) {
            int parent = index;
            int child = 2 * parent + 1;
            while (child <size) {
                if (child + 1 < size && array[child + 1] > array[child]) {
                    child = child + 1;
                }
                if (array[child] > array[parent]) {
                    int tmp = array[child];
                    array[child] = array[parent];
                    array[parent] = tmp;
                }else {
                    break;
                }
                parent = child;
                child = 2 * parent + 1;
            }
        }

        public Integer peek() {
            //取队首元素[O(1)]
            if(size == 0) {
                return null;
            }
            return array[0];
        }
        public boolean isEmpty() {
            return size == 0;
        }
        public static void main(String[] args) {
            MyPriorityQueue queue = new MyPriorityQueue();
            queue.offer(5);
            queue.offer(3);
            queue.offer(8);
            queue.offer(2);
            queue.offer(4);
            queue.offer(6);
            queue.offer(9);
            while (!queue.isEmpty()) {
                Integer cur = queue.poll();
                System.out.println(cur);
            }
        }
    }
}
