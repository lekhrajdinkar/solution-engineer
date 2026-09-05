# Sliding window : vary size
## Reference
- https://www.hellointerview.com/learn/code/sliding-window/variable-length

## Template 
- We use two pointers, `start` and `end`, to represent the start and end indices of the window.
- Next we repeatedly **extend** the current window incrementing `end`
- Eventually, we reach a window that is **invalid** because it contains 3 distinct fruits.
- Here, we **contract** the window by decrementing from `start`
- At this point, our window is ready to expand again, so we continue iterating until we reach the end of the array
-  $O(n)$ time | $O(1)$ space
> Pattern: searching for a continuous subarray/substring

```python
def variable_length_sliding_window(nums):
  state = # choose appropriate data structure
  start = 0
  max_ = 0
  for end in range(len(nums)):
    # extend window
    # add nums[end] to state in O(1) in time
    while state is not valid:
      # repeatedly contract window until it is valid again
      # remove nums[start] from state in O(1) in time
      start += 1
    # INVARIANT: state of current window is valid here.
    max_ = max(max_, end - start + 1)
  return max_
```
---
## 904 | Fruit Into Baskets
- https://leetcode.com/problems/fruit-into-baskets/
- https://www.hellointerview.com/learn/code/sliding-window/variable-length
- `len(state) > basket` | state is dict, basket is int

[02_02_vary-window.excalidraw](../draw/03/02/02_02_vary-window.excalidraw)

@[code:section::section-1](../../../../src/leetcode/hellointerview/slidingWindow/leetcode-sliding-904.py)


## 004. longest-substring (Distinct)
- https://leetcode.com/problems/longest-substring-without-repeating-characters/
- https://www.hellointerview.com/learn/code/sliding-window/longest-substring-without-repeating-characters

@[code:section::leetcode-004](../../../../src/leetcode/hellointerview/slidingWindow/leetcode-sliding-4.py)


---
## 424. Longest Repeating Character Replacement
- https://leetcode.com/problems/longest-repeating-character-replacement/description/
- https://www.hellointerview.com/learn/code/sliding-window/longest-repeating-character-replacement

@[code:section::leetcode-004](../../../../src/leetcode/hellointerview/slidingWindow/leetcode-sliding-4.py)
