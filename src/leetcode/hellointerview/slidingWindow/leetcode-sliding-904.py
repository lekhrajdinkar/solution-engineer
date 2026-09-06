from typing import List
class Solution:
    # section::section-1::start
    def totalFruit(self, fruits: List[int]) -> int:
        start = 0
        state = {} # dict, which is present in both moving ends, conditions ⭐
        basket = 2 # one for each fruit (specific condition o this program: having max 2 bucket) 👩🏿‍💻

        # Template-part-1 ⭐
        # Moving right side of window  -->
        # that make it dynamic size to window
        for end in range(len(fruits)):
            state[fruits[end]] = state.get([fruits[end]], 0) + 1 # state change-1 📝

            """
            === Fixed window : template ===
            if len(state) > basket:
                ...
                # Prep for next window
                state = state-nums[start]
                start +=1
            """

            # Template-part-2 ⭐
            # Moving left side of window, -->
            # while loop on state condition, makes it dynamic size
            while len(state) > basket:
                state[fruits[start]] -= 1 # state change-2 📝
                if state[fruits[start]] == 0: # edge case: after decrement, if count become then remove the key itself
                    del state[fruits[start]]

                start +=1 # increment is not +1 anymore, compare to fixed window

    # section::section-1::end