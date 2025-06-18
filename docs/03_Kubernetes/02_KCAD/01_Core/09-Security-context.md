## Security context
```
pod > container >
    
      securityContext:  # can also add metadata level (pod level)
        runAsUser: <userid>
        capabilities: # only supported here
          add: ["MAC_ADMIN"]
```