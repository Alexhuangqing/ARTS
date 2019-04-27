package weeks;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @Author Alex
 * @Desc
 * <p>
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * 有效字符串需满足：
 *     左括号必须用相同类型的右括号闭合。
 *     左括号必须以正确的顺序闭合。
 *     注意空字符串可被认为是有效字符串。
 * </p>
 * @Date 2019/4/22 23:11
 */
public class Week4IsValid {



    @Test
    public void test1(){
        boolean valid = isValid("()(){}[]{]");
        System.out.println(valid);
    }



    private  final static Map<Character,Character> symbolMap = new HashMap<>();
    static {
        symbolMap.put(')','(');
        symbolMap.put('}','{');
        symbolMap.put(']','[');
    }

    /**
     * 1.使用‘栈’ 数据结构
     * 2.左括号入栈，右括号出栈
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        if("".equals(s)){
            return true;
        }
        //栈容器
        Stack<Character> stack = new Stack();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if(symbolMap.containsKey(c)){
                //右符号用于出栈检测
                if(stack.isEmpty()){
                    return false;
                }
                Character pop = stack.pop();
                if(pop==null||!pop.equals(symbolMap.get(c))){
                return false;
                }
            }else{
                //左符号入栈或者其他入栈
                stack.push(c);
            }
        }
        //检测栈容器
        if(stack.isEmpty()){
            return true;
        }

        return false;
    }



}
