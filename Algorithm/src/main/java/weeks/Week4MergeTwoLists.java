package weeks;

import org.junit.Test;

/**
 * @Author Alex
 * @Desc
 * <p>
 *     将两个有序链表合并为一个新的有序链表并返回。
 *     新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 *
 *
 * </p>
 * @Date 2019/4/27 11:11
 */
public class Week4MergeTwoLists {
    @Test
    public void test(){

    }

    /**
     * 合并两个有序的单向链表
     * @param l1 有序
     * @param l2 有序
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1==null){
         return l2;
        }
        if(l2==null){
         return l1;
        }
        ListNode head = new ListNode(0);
        ListNode tail = head;
        //小节点向前移动
        //定义一个指针标识较小节点
//        ListNode minNode=l1;

        while(l1!=null&&l2!=null){
            //1.构造新链路的下一个节点
            // 并更新新链路的next指向
            // 更新新链路尾节点
            ListNode newNode = new ListNode(0);
            tail.next= newNode;
            tail=newNode;
            //2.旧链路 谁小处理谁，谁大谁等待
            if(l1.val<=l2.val){
                newNode.val=l1.val;
                l1 = l1.next;
            }else {
                newNode.val=l2.val;
                l2 = l2.next;
            }

        }
        //判断是哪个条件触发false条件
        if(l1==null){
            tail.next=l2;
        }
        if(l2==null){
            tail.next=l1;
        }
        //真正的节点开始初始节点的next
       return head.next;
    }



    public static class ListNode{
        int val;
        ListNode next;
        public ListNode(int val){
            this.val = val;
        }
    }
}
