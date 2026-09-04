# Sliding window
## Reference
- https://www.hellointerview.com/learn

## Template
> pattern: searching for a continuous subarray/substring
- Sliding windows can be either **variable** or **fixed length**
- but the implementation is a bit simpler.
- during each iteration, you both add and remove an element from the window to maintain its fixed size

```python
def fixed_length_sliding_window(nums, k):
    state = # choose appropriate data structure
    start = 0
    max_ = 0
    for end in range(len(nums)):
        # extend window
        # add nums[end] to state in O(1) in time
        if end - start + 1 == k:
            # INVARIANT: size of the window is k here.
            max_ = max(max_, contents of state)
            # contract window
            # remove nums[start] from state in O(1) in time
            start += 1
    return max_
```
---
## 00. Maximum Sum of Subarrays of Size K
- https://www.hellointerview.com/learn/code/sliding-window/maximum-sum-of-subarrays-of-size-k

[02_01_max_window_sum.excalidraw](../draw/03/02/02_01_max_window_sum.excalidraw)

```python
class Solution:
    def maxSum(self, nums: List[int], k: int) -> int:
        start = 0
        max_sum = float('-inf') # the smallest possible value
        sum1 = 0
        for end in range(len(nums)):
            sum1 = sum1 + nums[end] # accumulate sum
            if end-start+1 == k:  # current window reached
                max_sum = max(max_sum, sum1)
                
                # prep for next window
                sum1 = sum1 - nums[start] # 1. sub from left
                start += 1 # 2. start pointer of next window
        return max_sum
```
---
## 2461. Maximum Sum of Distinct Subarrays With Length K
- https://leetcode.com/problems/maximum-sum-of-distinct-subarrays-with-length-k/description/
- https://www.hellointerview.com/learn/code/sliding-window/maximum-sum-of-distinct-subarrays-with-length-k ✔️
  - add dict to track count of each num count
  - len(dict1) = k , then its distinct
  - dont forget to `increment` and `decrement` **count dict**:
    - `dict1[nums[start]] -= 1`
    - `dict1[nums[end]] = dict1.get(nums[end], 0) + 1`

@[code:3-10](../../../../src/leetcode/hellointerview/slidingWindow/leetcode-2461.py)
---
## 1423. Maximum Points You Can Obtain from Cards
> 💡inversion: outside window pattern
- https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/description/
-  look for min sum , rather than max window sum.
- if we find anything min inside window, then outside window will become max.

[1423_sliding-window.excalidraw](../draw/03/02/1423_sliding-window.excalidraw)

@[code:13-end](../../../../src/leetcode/hellointerview/slidingWindow/leetcode-1423.py)
