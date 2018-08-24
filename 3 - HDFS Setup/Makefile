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

mount-src-harish:
	@#./run vm1 mkdir -p "~/hdfs-setup/src-harish"
	@echo sshfs hthuwal@$(MY_IP):"/home/hthuwal/IITD/COP701/hdfs-setup/src" ~/hdfs-setup/src-harish

mount-src-zafar:
	@#./run vm1 mkdir -p "~/hdfs-setup/src-zafar"
	@echo sshfs dufferzafar@$(MY_IP):"/home/dufferzafar/dev/cloud-computing/3 - HDFS Setup/" ~/hdfs-setup/src-zafar

# Sync files to baadal machines
sync:
	@grep -v "^#" hosts | cut -f2 | xargs -I{} rsync -a --info=progress2 . {}:"~/hdfs-setup/"

# Change paths
edit-hdfs-site:
	@./run-all "cp ~/hdfs-setup/hadoop/hdfs-site.xml ~/hadoop/etc/hadoop/hdfs-site.xml"

	@./run vm1 "sed -i 's/vm\*/baadalservervm/'      ~/hadoop/etc/hadoop/hdfs-site.xml"
	@./run vm2 "sed -i 's/vm\*/baadalvm/'            ~/hadoop/etc/hadoop/hdfs-site.xml"
	@./run vm3 "sed -i 's/vm\*/baadalservervm/'      ~/hadoop/etc/hadoop/hdfs-site.xml"
	@./run vm4 "sed -i 's/vm\*/baadalvm/'            ~/hadoop/etc/hadoop/hdfs-site.xml"

# TODO: Per VM folders
.PHONY: logs
logs:
	@rm -rf logs/
	@mkdir -p logs/vm1/ logs/vm2/ logs/vm3/ logs/vm4/
	@rsync -r vm1:~/hadoop/logs/ logs/vm1/
	@rsync -r vm2:~/hadoop/logs/ logs/vm2/
	@rsync -r vm3:~/hadoop/logs/ logs/vm3/
	@rsync -r vm4:~/hadoop/logs/ logs/vm4/

show-ports:
	@./run-all "netstat -ntulp"

# https://askubuntu.com/questions/87665
change-hostname:
	@grep -v "^#" hosts | cut -f2 | xargs -P0 -I{} ./run -sudo {} hostname {}
	@grep -v "^#" hosts | cut -f2 | xargs -P0 -I{} ./run -sudo {} sed -i s/baadalvm/{}/ /etc/hostname # change hostname
	# @grep -v "^#" hosts | cut -f2 | xargs -P0 -I{} ./run -sudo {} sed -i s/baadalvm/{}/ /etc/hosts # comment the 127.0.1.1 line

format: stop-hdfs
	@./run-all "rm -rf ~/hdfs/"
	@./run-all "mkdir -p ~/hdfs/namenode && mkdir -p ~/hdfs/datanode"
	@./run vm1 "~/hadoop/bin/hadoop namenode -format"

# rm-current:
# 	@./run-all "rm -rf ~/hdfs/datanode/current"

link-config:
	@./run-all "for f in \$(ls ~/hdfs-setup/hadoop); do ln -f ~/hdfs-setup/hadoop/\$f ~/hadoop/etc/hadoop/\$f; done"
