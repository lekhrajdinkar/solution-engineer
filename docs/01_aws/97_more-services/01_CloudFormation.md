## Cloud Formation + composer
- IAC, stack, No manual resource creation
- declarative programing - yaml template, automatically detect what nneds to create first and so on.
- cost saving : automatically - delete/re-create some resource; schedule it.
- yaml --> `composer`, helps to see `visual` all resource and relations b/w them.
- it adds some tag to each resource.
- replace > upload new yaml > `changeSet` > add/update/delete, replace=t/f
- permission : CF > stack-1(Attach Role-1::policy::s3&sqs) > stack-1 can create/update/delete s3 and sqs only.
- ![img.png](../99_img/moreSrv/cf+ses+pinpoint/img.png)

