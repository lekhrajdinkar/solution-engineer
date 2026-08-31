# Sorting
## reference
- https://gemini.google.com/app/7ed3618cadf2e93d
- https://www.w3schools.com/dsa/dsa_algo_bubblesort.php

---
## 1. Bubble Sort 🫧

> 💡Key Idea: 
> - Repeatedly **compare adjacent pairs** and swap them if they are in the wrong order.
> - The **largest unsorted element** "bubbles up" to its correct position **at the end** of each pass.

**Working**
- Iterate through the array from the first element to the end.  
- Compare adjacent elements arr[j] and arr[j + 1].
- If arr[j] > arr[j + 1], swap them.  
- Track if any swap happened in the current pass using a flag.
- If an entire pass completes with zero swaps, break early because the array is already sorted.

**Complexity**
- Time Complexity:  $O(n^2)$
- Space Complexity: $O(1)$ (In-place)

[01_01_bubble-sort.excalidraw](draw/01_01_bubble-sort.excalidraw)


---
## 2. Selection Sort

> 💡Key Idea: 
> - Divide the array into sorted and unsorted regions. 
> - Scan the entire unsorted region to **find the global minimum**,
> - then swap it into the **front**.

**Working:**
- Maintain a boundary pointer ` i` **separating** the sorted prefix from the unsorted suffix.
- Search the entire unsorted region [i ... n-1] to locate the index of the **absolute minimum** element.
- Perform a single swap: exchange the **minimum element with arr[i].**
- **increment** the sorted boundary i by 1 and repeat until the array is sorted.  
- thus, Minimizes swap operations to exactly **one swap per outer pass**. 👈

**Complexity:**
- Time Complexity: Best/Average/Worst: $O(n^2)$ 
- Space Complexity: $O(1)$ (In-place)

[01_02_selection-sort.excalidraw](draw/01_02_selection-sort.excalidraw)

---
## 3. Insertion Sort ⭐⭐

> 💡Key Idea:
> - Mimics sorting playing cards in hand. 
> - Take one item at a time from the unsorted portion and shift larger sorted items to the right to insert it into its correct position

**Working:**
- Assume the first element arr[0] forms a sorted subarray of size 1.
- Pick the next element as the key.
- Compare the key **backwards against items** in the sorted portion (j = i - 1 down to 0).
- Shift elements that are greater than key one position to the right (arr[j + 1] = arr[j]).
- Break the comparison loop as soon as an element $\le$ key is encountered.
- Insert the key into the created empty slot (arr[j + 1] = key).

**Complexity:**
- Time Complexity: $O(n)$ - $O(n^2)$   ?
- Space Complexity: $O(1)$ (In-place)

[01_03_insertion-sort.excalidraw](draw/01_03_insertion-sort.excalidraw)