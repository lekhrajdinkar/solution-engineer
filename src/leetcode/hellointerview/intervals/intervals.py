from tokenize import endpats
from typing import List

from src.leetcode.hellointerview.intervals import util


# https://www.hellointerview.com/learn/code/intervals/can-attend-meetings
# leetcode locked

class Solution:
    # section::section-1::start
    def canAttendMeetings(self, intervals: List[List[int]]) -> bool:
        #for i in intervals:print(i)
        intervals.sort(key= lambda x: x[0], reverse=False) # Sort bt start time
        for i in intervals:print(i)
        for i in range(len(intervals)-1): # dont need to check for last item, so 1 less
            # curr internal and next interval has no overlapping
            # in any overlapping found, break right away with false
            if intervals[i][1] > intervals[i+1][0]:
                print("cant attend all meeting")
                return False
        print("cant attend all all meeting")
        return True
    # section::section-1::end

    # section::section-57::start
    """
    Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
    Output: [[1,5],[6,9]]
    
    Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
    Output: [[1,2],[3,10],[12,16]]
    """
    def insert(self, intervals: List[List[int]], newInterval: List[int]) -> List[List[int]]:
        print("\n\n======== leetcode : 57 : merge meeting =======",end=" ")
        print(f"\noriginal:      {intervals} ",end=" ")

        intervals.append(newInterval)
        print(f"\n1. append:  {intervals}",end=" ")

        intervals.sort(key= lambda x: x[0]) # Sort bt start time
        print(f"\n2. sort:    {intervals}",end=" ")

        print(f"\n3. Merge:    {intervals}",end=" ")
        for i in range(0, len(intervals)-1):
            print(f"\n- interval {i}, compare {intervals[i]} {intervals[i+1]} :", end=" ")
            if intervals[i][1] >= intervals[i+1][1]: # if current interval's end, overlaps with "end of next" (case 3)
                intervals[i+1][0] = intervals[i][0]
                intervals[i+1][1] = intervals[i][1]
                intervals[i][0],  intervals[i][1] = -1,-1 # Mark for delete / soft delete
                print(f"{intervals}", end=" ")
            elif intervals[i][1] >= intervals[i+1][0]: # if current interval's end, overlaps with "start of next" ()
                intervals[i+1][0] = intervals[i][0]
                intervals[i][0],  intervals[i][1] = -1,-1 # Mark for delete / soft delete
                print(f"{intervals}", end=" ")
            else:
                print(f"no change",  end=" ")

        res = list(filter(lambda i: i[0] != -1, intervals)) # filter, soft delete from final result ⭐
        print(f"\nafter cleanup: {res}",end=" ")
        return res
    # section::section-57::end

    # section::section-57-console::start
    """
    ======== leetcode : 57 : merge meeting ======= 
    original:      [[1, 3], [6, 9]] 
    after append:   
    after sort:    [[1, 3], [2, 5], [6, 9]] 
    - interval 0, compare [1, 3] [2, 5] : [[0, 0], [1, 5], [6, 9]] 
    - interval 1, compare [1, 5] [6, 9] : no change 
    after cleanup: [[1, 5], [6, 9]] 
    
    ======== leetcode : 57 : merge meeting ======= 
    original:      [[1, 2], [3, 5], [6, 7], [8, 10]] 
    after append:   
    after sort:    [[1, 2], [3, 5], [5, 6], [6, 7], [8, 10]] 
    - interval 0, compare [1, 2] [3, 5] : no change 
    - interval 1, compare [3, 5] [5, 6] : [[1, 2], [0, 0], [3, 6], [6, 7], [8, 10]] 
    - interval 2, compare [3, 6] [6, 7] : [[1, 2], [0, 0], [0, 0], [3, 7], [8, 10]] 
    - interval 3, compare [3, 7] [8, 10] : no change 
    after cleanup: [[1, 2], [3, 7], [8, 10]] 
    
    ======== leetcode : 57 : merge meeting ======= 
    original:      [[1, 2], [3, 5], [6, 7], [8, 10], [12, 16]] 
    after append:   
    after sort:    [[1, 2], [3, 5], [4, 8], [6, 7], [8, 10], [12, 16]] 
    - interval 0, compare [1, 2] [3, 5] : no change 
    - interval 1, compare [3, 5] [4, 8] : [[1, 2], [0, 0], [3, 8], [6, 7], [8, 10], [12, 16]] 
    - interval 2, compare [3, 8] [6, 7] : [[1, 2], [0, 0], [0, 0], [3, 8], [8, 10], [12, 16]] 
    - interval 3, compare [3, 8] [8, 10] : [[1, 2], [0, 0], [0, 0], [0, 0], [3, 10], [12, 16]] 
    - interval 4, compare [3, 10] [12, 16] : no change 
    after cleanup: [[1, 2], [3, 10], [12, 16]] 
    Process finished with exit code 0
    """
    # section::section-57-console::end

    # section::section-435::start
    def eraseOverlapIntervals(self, intervals: List[List[int]]) -> int:
        intervals.sort(key= lambda x: x[1]) # Sort bt start time
        #util.drawbar(intervals)
        non_overlap_count = 1 # first item in not-overlapping
        end  = intervals[0][1] # first interval :: end
        for i in range(len(intervals)):
            # find non-overlapping
            if intervals[i][0] >= end:
                end = intervals[i][1] # update end time for next it
                non_overlap_count += 1

        overlap_count = len(intervals) - non_overlap_count
        print(f"\noverlapping intervals need to remove , count : {overlap_count}")
        return overlap_count
    # section::section-435::end

# ==================== main ==============

i1 = [[1,50],[3,9],[6,8]]
Solution().canAttendMeetings(i1)

Solution().insert([[1,3],[6,9]], [2,5])
Solution().insert([[1,2],[3,5],[6,7],[8,10]], [5,6])
Solution().insert([[1,2],[3,5],[6,7],[8,10],[12,16]],  [4,8])
Solution().insert([[1,3],[6,9]],  [2,5])
Solution().insert([[1,5]],  [0,3])
Solution().insert([[1,5]],  [0,0])


Solution().eraseOverlapIntervals([[1,2],[2,3],[3,4],[1,3]])
Solution().eraseOverlapIntervals([[1,2],[1,2],[1,2]])



