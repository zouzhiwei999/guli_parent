import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/8/17 20:52
 */
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int result = solution.lengthOfLongestSubstring("abcab");
        System.out.println(result);
    }
    public int lengthOfLongestSubstring(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<Character>();
        int n = s.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
//            if (i != 0) {
//                // 左指针向右移动一格，移除一个字符
//                occ.remove(s.charAt(i - 1));
//            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }

    @Test
    public void test(){
        String s = "abcdef";
        char c = s.charAt(1);
        System.out.println(c);
    }

    @Test
    public void lengthOfLongestSubstring(){

        String s = "ababc";
        //获取最大不重复字串长度，利用窗口发，左边界做大循环 ，每次往右移动一位，右边界，每次往右移动一位
        //例如 abcabcd
        // a b c  判断长度
        // b c a  判断长度
        // c a b  判断长度
        // a b c d  判断长度
        //遇到相同的字符就中断小循环

        //获取字符串长度,左右边界都不能超过它
        int length = s.length();
        //声明最大不重复字串长度的返回值
        int len = 0;
        //右边界
        int right = 0;

        Set<Character> set = new HashSet<>();

        //开始大循环,i为左边界
        for (int i = 0; i < length; ++i) {
            //1.获取字符
            //2.循环判断唯一性集合中是否该字符，如果没有，存入唯一性集合，如果有，中断小循环
            while (right < length){
                if (!set.contains(s.charAt(right))){
                    set.add(s.charAt(right));
                    right = right + 1;
                } else {
                    break;
                }
            }
            //3.判断是否最长长度,右边界-左边界+1
            len = Math.max(len, right-(i));
        }
        //输出结果
        System.out.println(set);
        System.out.println(len);
    }
}
