package weeks;

import org.junit.Test;

/**
 * @Author Alex
 * @Desc
 * <p>
 *判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * </p>
 * @Date 2019/4/16 6:05
 */
public class Week3IsPalindrome {

    @Test
    public void tes1(){
        System.out.println(isPalindrome1(121));
        System.out.println(isPalindrome1(1901));
        System.out.println(isPalindrome1(19091));
        System.out.println(isPalindrome2(121));
        System.out.println(isPalindrome2(1901));
        System.out.println(isPalindrome2(19091));
    }

    /**
     * 利用字符串来拓展增加空间复杂度
     * @param x
     * @return
     */
    private boolean isPalindrome1(int x){
        String intStr = String.valueOf(x);
        int start = 0;
        int end = intStr.length()-1;
        while(start <= end ){
            if(intStr.charAt(start++)-intStr.charAt(end--)!=0){
                return false;
            }
        }
        return true;
    }

    /**
     * 利用一般数字与反转后的数字比较；
     * 取一般即可
     * @param x
     * @return
     */
    private boolean isPalindrome2(int x){

        //快速失败的判断
        if(x<0||(x%10==0&&x!=0)){
            return false;
        }
        // x位数缩减，reserveNum位数增加，
        // 因此两者比较理解为数量级的比较
        int reserveNum = 0;
        while(x>reserveNum){
            reserveNum = reserveNum*10 + x%10; //尾数出栈，在入栈
            x/=10; //裁掉尾数
        }

        //长度是整数，刚好在一个数量级上，相等即可
        //长度是奇数，reserveNum数量级比x多一位，reserveNum裁掉尾数比较即可
        return reserveNum==x||reserveNum/10==x;
    }

}
