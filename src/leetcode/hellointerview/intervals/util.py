def drawbar(intervals: list):
    import matplotlib.pyplot as plt
    from matplotlib.ticker import MaxNLocator
    fig, ax = plt.subplots(figsize=(10, 5))
    for i, (start, end) in enumerate(intervals):
        ax.barh(
            y=i,
            width=end - start,
            left=start,
            height=0.6
        )
        ax.text(
            start,
            i,
            f" [{start}, {end}]",
            va="center"
        )
    ax.set_yticks(range(len(intervals)))
    ax.set_yticklabels(
        [f"Interval {i + 1}" for i in range(len(intervals))]
    )
    ax.xaxis.set_major_locator(MaxNLocator(integer=True))
    ax.set_xlabel("Time")
    ax.set_ylabel("Interval")
    ax.set_title("Intervals")
    ax.invert_yaxis()
    min_start = min(start for start, end in intervals)
    max_end = max(end for start, end in intervals)
    ax.set_xlim(min_start - 0.5, max_end + 0.5)
    ax.grid(axis="x", linestyle="--", alpha=0.4)
    plt.tight_layout()
    plt.show()