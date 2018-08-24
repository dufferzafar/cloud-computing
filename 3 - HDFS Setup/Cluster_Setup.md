
# Cluster Setup

* **VM Mapping**

```
vm1 | COL733_Chrystle_mcs172102_04   | 10.17.50.109 | Server  | baadalservervm
vm2 | COL733_Harish_mcs172074_04     | 10.17.50.169 | Desktop | baadalvm
vm3 | COL733_Shadab_mcs172076_04     | 10.17.51.41  | Server  | baadalservervm
vm4 | COL733_Keshav_csz178058_04_new | 10.17.6.91   | Desktop | baadalvm
```

## Log

* Changed passwords of all machines to: `Cl0udC`
    - Ran `make root-pass` 
    - Ran `make user-pass`
        + see `Makefile` for these commands

* Added hosts information to our machines
    - `sudo su`; `cat hosts >> /etc/hosts`
    <!-- - sudo bash -c "cat hosts >> /etc/hosts" -->

* Passwordless SSH
    - From our laptops
    
        + For `baadal` users
            * `cut -f2 hosts | grep -v "^#" | xargs -I{} ssh-copy-id -i ~/.ssh/id_rsa.pub {}`
        
        + For `root`:
            * `cut -f2 hosts | grep -v "^#" | xargs -I{} ssh-copy-id -i ~/.ssh/id_rsa.pub root@{}`

    - Between baadal machines
        + 


* SSH Config file
    - Because machines have different usernames
