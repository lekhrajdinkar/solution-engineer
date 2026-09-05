from typing import List

# https://leetcode.com/problems/maximum-sum-of-distinct-subarrays-with-length-k/
# 2461. Maximum Sum of Distinct Subarrays With Length K
class Solution:
   # section::mySolution::start
    def maxSum_1(self, nums: List[int], k: int) -> int:
        start = 0
        max_sum = 0;  curr_sum = 0; tempSubArray = [] # 3 states

        for end in range(len(nums)):
            curr_sum = curr_sum + nums[end] # accumulate sum
            tempSubArray.append(nums[end] ) ## <<<---------

            # current window reached
            if end-start+1 == k:
                tempSubArraySet = set(tempSubArray)
                if len(tempSubArraySet) == len(tempSubArray): ## <<<---------
                    max_sum = max(max_sum, curr_sum)

                # =========template (2 steps) =======
                # Step-1 - subtract from sum - left item's ⭐
                curr_sum = curr_sum - nums[start]
                tempSubArray.pop(0) ## <<<----------

                # Step-2 - increment counter to next window,
                # once current window traversed, till k item
                start += 1

        return max_sum
    # section::mySolution::end

    # section::helloInterview::start
    # rather than using set, then used dict to track duplicates, which easy and preferred ⭐
    def maxSum(self, nums, k):
        max_sum = float("-inf")
        start = 0
        state = {}
        curr_sum = 0
        for end in range(len(nums)):
            curr_sum += nums[end]
            state[nums[end]] = state.get(nums[end], 0) + 1
            if end - start + 1 == k:
                if len(state) == k:
                    max_sum = max(max_sum, curr_sum)
                curr_sum -= nums[start]
                state[nums[start]] -= 1
                if state[nums[start]] == 0:
                    del state[nums[start]]
                start += 1
        return 0 if max_sum == float("-inf") else max_sum
   # section::helloInterview::end

if __name__ == '__main__':
    Solution().maxSum_1( [1,5,4,2,9,9,9], 3)
    Solution().maxSum( [1,5,4,2,9,9,9], 3)