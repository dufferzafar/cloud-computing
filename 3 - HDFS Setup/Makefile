# 												| tr -s " " | cut -d" " -f3
MY_IP := $(shell ifconfig wlp2s0 | grep "inet " | awk '{print $$2}')

start-hdfs:
	./run vm1 "~/hadoop/sbin/hadoop-daemon.sh" start namenode

	./run vm2 "~/hadoop/sbin/hadoop-daemon.sh" start datanode
	./run vm3 "~/hadoop/sbin/hadoop-daemon.sh" start datanode
	./run vm4 "~/hadoop/sbin/hadoop-daemon.sh" start datanode

stop-hdfs:
	./run vm1 "~/hadoop/sbin/hadoop-daemon.sh" stop namenode

	./run vm2 "~/hadoop/sbin/hadoop-daemon.sh" stop datanode
	./run vm3 "~/hadoop/sbin/hadoop-daemon.sh" stop datanode
	./run vm4 "~/hadoop/sbin/hadoop-daemon.sh" stop datanode

restart-all:
	./run-all -sudo shutdown -r now

hdfs-du:
	./run-all du -h -d0 "~/hdfs"

hdfs-report:
	./run vm1 "~/hadoop/bin/hdfs" dfsadmin -report

# By default it asks to change password
root-pass:
	ssh root@vm1
	ssh root@vm2
	ssh root@vm3
	ssh root@vm4

user-pass:
	ssh vm1
	ssh vm2
	ssh vm3
	ssh vm4

# Sync files to baadal machines
sync:
	@grep -v "^#" hosts | cut -f2 | xargs -I{} rsync -a --info=progress2 . {}:"~/hdfs-setup/"

link-config:
	@./run-all "for f in \$(ls ~/hdfs-setup/hadoop); do ln -f ~/hdfs-setup/hadoop/\$f ~/hadoop/etc/hadoop/\$f; done"

# Change paths
edit-hdfs-site:
	@./run-all "cp ~/hdfs-setup/hadoop/hdfs-site.xml ~/hadoop/etc/hadoop/hdfs-site.xml"

	@./run vm1 "sed -i 's/vm\*/baadalservervm/'      ~/hadoop/etc/hadoop/hdfs-site.xml"
	@./run vm2 "sed -i 's/vm\*/baadalvm/'            ~/hadoop/etc/hadoop/hdfs-site.xml"
	@./run vm3 "sed -i 's/vm\*/baadalservervm/'      ~/hadoop/etc/hadoop/hdfs-site.xml"
	@./run vm4 "sed -i 's/vm\*/baadalvm/'            ~/hadoop/etc/hadoop/hdfs-site.xml"

# Download logs from all machines into a local folder
# Used for debugging
.PHONY: fetch-logs
fetch-logs:
	@rm -rf logs/
	@mkdir -p logs/vm1/ logs/vm2/ logs/vm3/ logs/vm4/
	@rsync -r vm1:~/hadoop/logs/ logs/vm1/
	@rsync -r vm2:~/hadoop/logs/ logs/vm2/
	@rsync -r vm3:~/hadoop/logs/ logs/vm3/
	@rsync -r vm4:~/hadoop/logs/ logs/vm4/

# Download config files from all machines into a local folder
# Used for preparing the report
.PHONY: fetch-config
fetch-config:
	@rm -rf configs/
	@mkdir -p configs/vm1/ configs/vm2/ configs/vm3/ configs/vm4/
	@rsync -r vm1:~/hadoop/etc/hadoop/ configs/vm1/
	@rsync -r vm2:~/hadoop/etc/hadoop/ configs/vm2/
	@rsync -r vm3:~/hadoop/etc/hadoop/ configs/vm3/
	@rsync -r vm4:~/hadoop/etc/hadoop/ configs/vm4/

# Used to confirm that java
show-ports:
	@./run-all "netstat -ntulp"

# https://askubuntu.com/questions/87665
change-hostname:
	@grep -v "^#" hosts | cut -f2 | xargs -P0 -I{} ./run -sudo {} hostname {}
	@grep -v "^#" hosts | cut -f2 | xargs -P0 -I{} ./run -sudo {} sed -i s/baadalvm/{}/ /etc/hostname # change hostname
	# @grep -v "^#" hosts | cut -f2 | xargs -P0 -I{} ./run -sudo {} sed -i s/baadalvm/{}/ /etc/hosts # comment the 127.0.1.1 line

# Used when
format: stop-hdfs
	@./run-all "rm -rf ~/hdfs/"
	@./run-all "mkdir -p ~/hdfs/namenode && mkdir -p ~/hdfs/datanode"
	@./run vm1 "~/hadoop/bin/hadoop namenode -format"
