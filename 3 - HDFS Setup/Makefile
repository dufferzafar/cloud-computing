MY_IP := $(shell ifconfig wlp2s0 | grep "inet addr:" | cut -d: -f2 | cut -d " " -f 1)

stop-all: stop-hdfs
start-all: start-hdfs

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

restart-all: stop-all
	./run-all sudo shutdown -r now


hdfs-du:
	./run-all du -h -d0 "~/hdfs"

hdfs-report:
	./run vm1 hdfs dfsadmin -report

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
	@#./run vm1 mkdir -p "~/github-analytics/src-harish"
	@echo sshfs hthuwal@$(MY_IP):/home/hthuwal/IITD/COP701/github-analytics/src /home/vm1/github-analytics/src-harish

mount-src-zafar:
	@#./run vm1 mkdir -p "~/github-analytics/src-zafar"
	@echo sshfs dufferzafar@$(MY_IP):/home/dufferzafar/dev/github-analytics/src /home/vm1/github-analytics/src-zafar
