# ytnote

A specialized skill for extracting video transcripts from YouTube and transforming them into detailed, production-grade technical study notes formatted in Markdown.

## Description
Generates comprehensive, structured Markdown notes from YouTube videos focusing on software engineering, system design, and AI engineering. 
Use when the user shares YouTube links, asks to take notes, create study guides, summarize, or extract technical concepts and interview tips from YouTube videos.

## When to Use

- When the user provides one or more YouTube video URLs to summarize or take notes on.
- When the user asks for structured concept notes or study guides from technical talks, system design lectures, or AI engineering tutorials.
- When the user wants architectural breakdowns, trade-off analyses, or interview-focused takeaways from video content.

## Workflow Steps

### 1. Extract Video Content

- Retrieve the video transcript and metadata using YouTube tools.
- If multiple video links are provided, process each link and create distinct or consolidated notes as requested.
- If the transcript is unavailable, notify the user and synthesize notes using available metadata.

### 2. Synthesize and Structure Technical Concepts

- Extract core architectural components, data flows, and design patterns.
- Detail practical production trade-offs, scaling considerations, and bottlenecks.
- Capture real-world examples and use cases mentioned in the video.

### 3. Generate Markdown Notes

Format the notes using clean, GitHub-compatible Markdown with a clear H1-H4 heading hierarchy:

- `# [Video Title / Topic]`
    - Metadata section with URL, channel/speaker, and topic tags.

- `## Overview`
    - High-level problem statement and executive summary.
    - diagram with mermaid, sketch kind, black text and colorful box for highlighting key item and emojis

- `## Key Concepts & Architecture`
    - `### Concept: [Name]`
        - In-depth explanation of core mechanisms, component interactions, and data flow.
        - `#### Implementation Details` (when applicable)

- `## Use Cases & Real-World Examples`
    - Concrete application scenarios and practical patterns.

- `## Trade-offs: Pros & Cons`
    - Analysis of advantages, disadvantages, performance, complexity, and operational costs.

- `## Interview Tips & Common Pitfalls`
    - Relevant technical/system design interview questions, high-signal talking points, and common traps.

- `## Summary & Key Takeaways`
    - Core principles and quick-reference summary bullets.

- `## Further Exploration & Related Topics`
    - Related technologies, design patterns, and topics for deeper study.

## Gotchas

- Preserve technical specificity: Retain exact terminology, protocol names, performance metrics, and component names from the lecture.
- Consistent Markdown: Ensure proper code blocks, lists, and heading nesting for clean rendering in GitHub repositories.
- take engineer diagram with mermaid and emojis