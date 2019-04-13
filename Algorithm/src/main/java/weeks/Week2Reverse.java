package weeks;

import org.junit.Test;

/**
 * @Author Alex
 * @Desc
 * <p>
 *    （1） 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 *    （2） 假设我们的环境只能存储得下 32 位的有符号整数，
 *          则其数值范围为 [−2^31,  2^31 − 1]。
 *          请根据这个假设，如果反转后整数溢出那么就返回 0。
 *      解法：栈溢出，事前与事后都可以检验
 *
 * </p>
 * @Date 2019/4/9 23:34
 */
public class Week2Reverse {
    @Test
    public void test1(){
        System.out.println(Integer.MAX_VALUE);
        System.out.println( Integer.MIN_VALUE);
    }
    @Test
    public void test3(){
        System.out.println(-1101%10); //取余数
        System.out.println(-1101/10); //取整数
    }

    @Test
    public void  test2(){
        int n1 = 123456;
        System.out.println(reverse(n1));
        System.out.println(reverse1(n1));
        System.out.println(reverse2(n1));
    }

    /**
     * 条件限制出现栈溢出
     * lose
     * @param x
     * @return
     */
    public int reverse(int x) {
        int result = 0;
        while (x != 0) {
            int pop = x % 10; //模拟出栈,原数取余
            x = x / 10;
            //限制条件 (1) 防止栈溢出 (2)检验条件是也要防止栈溢出
            if (result * 10 + pop > Integer.MAX_VALUE ||
                result * 10 + pop < Integer.MIN_VALUE
            ) {
                return 0;
            }
            result = result * 10 + pop; //模拟入栈，原数*10+pop
        }
        return result;
    }


    /**
     *
     * @param x
     * pass
     * @return
     */
    public int reverse1(int x) {
        int result = 0;
        while (x != 0) {
            int pop = x % 10; //模拟出栈,原数取余
            x = x / 10;
            //限制条件 (1) 防止栈溢出 (2)检验条件是也要防止栈溢出
            // result*10+pop > Integer.MAX_VALUE 分解成超出数量级（如条件超过数量级直接回栈溢出），与恰好在同数量级
            // result*10+pop < Integer.MIN_VALUE 分解
            if (result > Integer.MAX_VALUE / 10 ||
                (result == Integer.MAX_VALUE / 10 && pop > Integer.MAX_VALUE - 10 * result)) {
                return 0;
            }
            if (result < Integer.MIN_VALUE / 10 ||
                (result == Integer.MIN_VALUE / 10 && pop < Integer.MIN_VALUE - 10 * result)) {
                return 0;
            }
            result = result * 10 + pop; //模拟入栈，原数*10+pop
        }
        return result;
    }


    /**
     *习得一种偷懒的解法：事后检验
     * pass
     * @param x
     * @return
     */
    public int reverse2(int x) {
        int ret = 0;
        while (x != 0) {
            int temp = ret * 10 + x % 10;//整数位左移一位 + 新进个位
            if (temp / 10 != ret) {//如果发生栈溢出后，事后检验
                return 0;
            }
            ret = temp;
            x/=10;
        }
        return ret;
    }




}
