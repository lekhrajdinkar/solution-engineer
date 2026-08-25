# Domain Driven design | DDD
## References
- https://youtube.com/watch?v=sVi4aDNIPWo 1. Introduction of DDD
- https://youtube.com/watch?v=eDpXY6KJiY8 2. Business Domains and Subdomains
- https://youtube.com/watch?v=OJNh4byblYI 3. Ubiquitous Language 
- https://youtube.com/watch?v=8SPVfacnFvM 4. Bounded Contexts

---
> theory

## 1. Introduction of DDD
A software development approach that prioritizes understanding the core business problem over focusing solely on technology

* **Foundations:** DDD was pioneered by *Eric Evans* in his seminal book published in the mid-2000s, aiming to create a common language between developers and business experts (0:21 - 0:43).
* **Domain vs. Technology:** Traditional development often starts with code architecture (objects, class, OOP, layers, patterns, modules, layer, project). DDD instead focuses on the **domain**—the actual business area, such as banking or e-commerce—and its smaller, focused **subdomains** (1:08 - 2:27).
* **Core Tooling:**
    * **Strategic Tools: (what and  why ?)** Used to analyze the business, identify key players, and define system boundaries
    * **Tactical Tools: (How ?)** Focus on implementing business logic in a clear, expressive way within the code

**The Bottom Line:**
While mastering new coding frameworks is important, *ByteMonk* argues that the true challenge for a software engineer is navigating complex business domains. DDD serves as a bridge, making developers more effective by ensuring their software solutions genuinely meet business needs (3:44 - 4:26).

---
## 2. Business Domains and Subdomains
- DDD helps software developers align their code with business goals 
by categorizing organizational activities into specific **subdomains**. 
- Understanding these domains is essential for building effective, value-driven software (0:00 - 0:54).

### **Key Concepts in DDD**
* **Business Domain:** The primary area of activity for a company, such as *Netflix's* streaming or *Tesla's* electric vehicles (0:54 - 1:53).
* **Subdomains:** The specific building blocks that make up the broader business domain. DDD categorizes these into three types based on their strategic value (1:53 - 3:44):
    * **Core Subdomains:** These provide a **competitive advantage** and differentiate a company from its rivals (e.g., *Uber’s* ride-sharing algorithm). They require in-house innovation and top talent (2:33 - 3:44, 5:40 - 6:39).
    * **Generic Subdomains:** These are necessary but common across many businesses (e.g., *user authentication*). Companies typically use off-the-shelf, proven solutions rather than building from scratch (3:44 - 4:06, 6:05 - 6:16).
    * **Supporting Subdomains:** These are essential for operations but do not drive competitive differentiation (e.g., *inventory management*). They are often simple and can be outsourced or handled by junior teams (4:08 - 4:23, 6:17 - 6:39).

### **Domain Experts**
* The video concludes by highlighting the role of **Domain Experts**—the subject matter specialists (like doctors in a hospital system) who bridge the gap between business needs and technical implementation (9:58 - 10:47).

---
## 3. Ubiquitous Language
- DDD  uses *Ubiquitous Language* and *Models* to align software development with real-world business needs.

### Key Concepts:

*   **Business Problems & Subdomains:** Software exists to solve specific ongoing business challenges (0:31-1:25). Organizations break these into *subdomains*—smaller, manageable areas of focus like payments or scheduling—to solve problems more effectively.
*   **The Communication Gap:** Project failure often stems from poor communication between domain experts and engineers (1:55-3:21). Traditional development risks losing context as requirements pass through various intermediaries and translation layers.
*   **Ubiquitous Language:** To bridge this gap, DDD encourages teams to adopt a shared vocabulary (3:21-4:59). This language:
    *   Uses business-centric terms rather than technical jargon.
    *   Maintains strict consistency to avoid ambiguity 
*   **Models:** 
    - Models act as simplified, purpose-driven maps of a business domain  
    - They allow teams to strip away unnecessary complexity 
    - and focus on the essential rules and logic needed to build the right solution.

### Practical Tools for Implementation:

1.  **Glossaries & Wikis:** Useful for documenting shared terms and helping new team members get up to speed (6:24-6:43).
2.  **Gherkin Tests:** Using plain language scenarios (e.g., "Given-When-Then") allows developers and domain experts to verify system behaviors collaboratively (6:43-7:14).
3.  **Refining Language:** Cultivating this language is an ongoing process that requires active discussion and a willingness to align documentation and code with business terminology (7:14-8:00).

---
## 4. Bounded Contexts

*   **The Problem of Ambiguity:** As software grows, terms like "student" or "booking" can mean different things to different teams (e.g., an enrollment team vs. a content team). Using a single, shared model across a whole system often leads to bloated, confusing, and unmanageable code (0:39–2:45).
*   **What is a Bounded Context?** It is a clear boundary within which a specific "ubiquitous language" is valid. It acts as a container for a specific part of the business domain, ensuring terms remain consistent and meaningful within that scope (2:48–3:51).
*   **Strategic Scope:** Defining the size of a context is a strategic decision. Too small can create integration overhead, while too large risks the problems of a monolith. Ideal boundaries are often aligned with specific business subdomains or team responsibilities (4:00–6:53).
*   **Subdomains vs. Bounded Contexts:** Subdomains are identified through business strategy (the "what"), while bounded contexts are designed through architectural implementation (the "how"). While they often correlate, they are distinct concepts (8:24–10:29).
*   **Physical and Ownership Boundaries:** Bounded contexts help structure the system physically (e.g., as independent microservices) and organizationally (e.g., one team per context). This minimizes inter-team conflicts and improves scalability (10:30–12:59).

**The "Tomato" Analogy:**
To summarize: Just as a tomato is a fruit in a *botanical* context but a vegetable in a *culinary* context, bounded contexts ensure that software models are accurate and focused based on the specific domain boundary they reside in (13:04–14:01).


