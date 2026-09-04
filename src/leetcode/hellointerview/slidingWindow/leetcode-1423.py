def maxScore(self, cardPoints: List[int], k: int) -> int:
    start = 0
    sumOfWindow = 0
    #maxScore = 0
    totalPoints = sum(cardPoints)
    print(totalPoints)
    n=len(cardPoints)
    minWindowSum = float('inf')

    # handle special case of leetcode
    if k == len(cardPoints): return totalPoints

    for end in range(n):
        sumOfWindow = sumOfWindow + cardPoints[end]

        if end-start+1 == n-k:
            #maxScore = max(totalPoints - sumOfWindow, maxScore)
            minWindowSum = min (minWindowSum, sumOfWindow) # look minimum ⭐

            sumOfWindow = sumOfWindow - cardPoints[start] # imp
            start +=1

    return totalPoints - minWindowSum # return inverted result ⭐