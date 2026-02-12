# kernel
https://www.youtube.com/watch?v=8afhcpiMh24

## Overview
- manages communication between software and hardware
- central program that acts as the brain of the operating system
- operates in a **privileged mode**, 
  - giving it full control over the CPU, memory, and devices
  - unlike applications that run in a limited user mode.
  
> its failure leads to system crashes like the "blue screen of death."

---
## Jobs
**Process Management** 
- It schedules programs, 
- determining their order 
- and duration on the CPU, 
- enabling multitasking.

**Memory Management** 
- The kernel allocates memory to programs, 
- ensuring they don't interfere with each other 
- and blocking unauthorized access.

**File I/O and Hardware Communication** 
- It handles system calls for file operations 
- and interactions with hardware components like Wi-Fi chips.

**Interrupt Handling** 
- The kernel responds to hardware signals (like a key press),
- pausing current tasks to address them before resuming.

---
## type
**Monolithic Kernels** 
- These designs, like Linux and Windows, 
- integrate most functions (memory management, file systems, device drivers) within the kernel space, 
- offering speed but risking **system-wide crashes** if a component fails.

**Microkernels** 
- These keep only essential job in the kernel, 
- running others as separate services in user space.
- This approach, used in macOS, 
- is slower but more secure as a service crash won't bring down the entire OS. 

---
## Specialized Kernels 
**GPU Programming**  
- In CUDA, a kernel is a small function that runs in parallel across GPU cores, performing low-level, hardware-near routines.

**AI Workloads** 
- These kernels process **tensors** and train models efficiently.

**Quantum Computing**
- Researchers are developing **quantum kernels** 
- to manage instruction execution on qubits.