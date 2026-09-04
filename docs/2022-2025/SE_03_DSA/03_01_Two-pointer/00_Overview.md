# two-pointers
## Reference
- https://www.hellointerview.com/learn/code/two-pointers/overview

---
##  283 | move zeros
- https://leetcode.com/problems/move-zeroes/
- https://www.tryexponent.com/courses/software-engineering/swe-practice/move-zeros-to-end-of-array-practice

[leetcode-283-move-zeros.excalidraw](../draw/03/01/leetcode-283-move-zeros.excalidraw)

```python
class Solution:
    def moveZeroes(self, nums: List[int]) -> None:
        print('Before ',nums)
        nums[:] = list(filter(lambda x: x!= 0, nums)) + ([0]*nums.count(0))
        print('After ',nums)

```
```python
class Solution:
    def moveZeroes(self, nums: List[int]) -> None:
        nextNonZero = 0
        for i in range(len(nums)):
            if nums[i] != 0:
                nums[i], nums[nextNonZero] =   nums[nextNonZero], nums[i]  # swap
                nextNonZero = nextNonZero + 1
```

---
## 75 | 3 Sort Color (Similar to move-zeros)
> single pass Sort 
- https://leetcode.com/problems/sort-colors/
- https://www.hellointerview.com/learn/code/two-pointers/sort-colors

[lc-75-3color-sort.excalidraw](../draw/03/01/lc-75-3color-sort.excalidraw)

```python
class Solution:
    def sortColors(self, nums: List[int]) -> None:
        # starting
        left = 0
        right = len(nums)-1
        i= 0
        
        # break when 
        while i <= right:
            if nums[i] == 0:
                nums[left], nums[i] =   nums[i], nums[left]
                i += 1
                left += 1
            
            elif nums[i] == 2:
                nums[right], nums[i] =   nums[i], nums[right]
                #i += 1
                right -= 1
            
            else:
                i += 1
```

---
##  11 | container-with-most-water
- https://leetcode.com/problems/container-with-most-water/description/
- similar to 2 Sum but, calculating area

[lc-11-water-container.excalidraw](../draw/03/01/lc-11-water-container.excalidraw)

```python
class Solution:
    def maxArea(self, heights: List[int]) -> int:
        n = len(heights)
        l = 0 # left pointer
        r = n-1 # right pointer
        max_a = 0 
        while l < r: # always move left and right inwards, until collides
            w = r - l # width
            h = min (heights[l], heights[r]) # height, pick min, since it wont overflow.
            a = w * h # new area
            max_a = max(a, max_a) # track result

             # if left is bigger, then moving it innward, will reduce area only, so no point. 
             # hence move right pointer  <<
            if heights[l] >= heights[r]:
                r = r - 1 # 
            else:
                l = l + 1
        
        return max_a
```
---
##  15 | 3 sum (for + while)
https://leetcode.com/problems/3sum/description/

[leetcode-15-3sum.excalidraw](../draw/03/01/leetcode-15-3sum.excalidraw)

```python
class Solution:
    def threeSum(self, nums: List[int]) -> List[List[int]]:

        nums.sort()
        result = set()
        n = len(nums)

        for i in range(n - 2):
            #if nums[i-1] == nums[i]: # Skip duplicates ?????
            if i > 0 and nums[i-1] == nums[i]:
                continue

            left, right = i + 1, n - 1
            while left < right:
                total = nums[i] + nums[left] + nums[right]
                if total == 0:
                    result.add((nums[i], nums[left], nums[right]))
                    left += 1
                    right -= 1
                elif total < 0:
                    left += 1
                else:
                    right -= 1

        return list(result)
```
---
##  167 | 2 Sum (while)
https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/

```python
class Solution:
    def twoSum(self, nums: List[int], target: int) -> bool:
        l, r = 0, len(nums) - 1
        while l < r:
            total = nums[l] + nums[r]
            if total == target:
                return True;
            else:
                if total > target:
                    r -= 1
                else:
                    l += 1
        
        return False;
```
---
## 161 | triangle (similar to 3 sum)
- https://leetcode.com/problems/valid-triangle-number/description/

[lc-161-triangle.excalidraw](../draw/03/01/lc-611-triangle.excalidraw)

```python
class Solution:
    def triangleNumber(self, nums: List[int]) -> int:
    nums.sort()
    res = 0
    for i in range(len(nums)-2):
        a = i
        b = i+1
        c = len(nums) - 1
        while b < c:
            if (nums[b] + nums[c] > nums[a]): # Tip 1 Dont need to check all for 3 
                res = res + (c - b) # Tip-2, since sorted no need to check for rest. a+b>c, then will always be (a+x)+b>c
                c = c-1 # c -= 1
            else:
                b = b+1 # b += 1
    return res
```



