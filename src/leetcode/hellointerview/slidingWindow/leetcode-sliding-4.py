from typing import List
class Solution:
    # section::leetcode-004-1::start
    def lengthOfLongestSubstring_1(self, s: str) -> int:
        n = len(s)
        tracker: dict = {}
        for i in range(n):
            # also consider first char as substr
            substr=s[i]
            tracker[substr] = len(s[i]) # can hardcode to 1 as well.

            # add first:more-char
            for j in range(i+1, n): # check repeating char
                if s[j] not in substr:
                    substr = s[i:j+1] # conditionally slide
                    tracker[substr] = len(substr)
                else :
                    break

        if not tracker: #empty tracker
            return 0
        else:
            return max(tracker.values())
    # section::leetcode-004-1::end

    # section::leetcode-004::start
    # "abcabcbb"
    def lengthOfLongestSubstring(self, s: str) -> int:
        state: dict = {} # { char : count }
        start = 0
        max_length = 0
        for end in range(0,len(s)):
            state[s[end]] = state.get(s[end], 0) + 1
            # max_length = max(max_length, len(s[start:end]))
            print(f"\nfetched '{s[end]}', subStr '{s[start:end+1]}', with len : {end-start+1} [{end}-{start}+1], state now: {state}", end="")

            #while all(value != 1 for value in state.values()):
            #while s[end] not in state.keys():
            #while len(set(state.values())) > 1:
            while state[s[end]] > 1:
                print(f"\n\thave duplicate char, ▶️slide window...", end="")
                state[s[start]] -= 1
                if state[s[start]] == 0: del state[s[start]]
                start += 1
                print(f"\tnew subStr `{s[start:end+1]}`, state now: {state}",  end="")

            max_length = max(max_length, end-start+1)
            print(f"\n\tnew subStr ⭐`{s[start:end+1]}` |  max_len tracker: {max_length}")

        print(f"\n=== Final result : {max_length} ===")
        return max_length
    # section::leetcode-004::end

    # section::leetcode-424::start
    # "abcabcbb"
    def characterReplacement(self, s: str, k: int) -> int:
        pass
    # section::leetcode-424::end

    def print_full_equilateral_pyramid(self, n: int):
        midIndex = (n-1)/2
        max_row = int((n/2)+1)
        #for i in range(0,max_row-1): # 👈👈
        for i in range(0,max_row):
            print("", end="\n")
            for j in range(n):
                if (midIndex - i) <= j <= (midIndex + i): # if j == midIndex:
                    print("*", end=" ")
                else:
                    print(" ", end=" ")
        """
                          *       
                        * * *     
                      * * * * *   
                    * * * * * * *  
        """

        for i in range(max_row-1, -1, -1): # 👈👈
            print("", end="\n")
            for j in range(n):
                if (midIndex - i) <= j <= (midIndex + i): # if j == midIndex:
                    print("*", end=" ")
                else:
                    print(" ", end=" ")

        """
                    * * * * * * * 
                      * * * * *   
                        * * *     
                          *  
        """



Solution().lengthOfLongestSubstring("bbbb")
Solution().lengthOfLongestSubstring("abcabc")
Solution().lengthOfLongestSubstring("abcdcc")
Solution().print_full_equilateral_pyramid(7)
