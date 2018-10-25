import os
import sys
import pdb
import random

def genGrade(maxGrade):
    return random.randint(0, maxGrade+1)

def genRecord(rollNo, course, grade):
    record = ""
    record = str(rollNo) + "\t" + "COL"+"%03d"%course + "\t" + str(grade)
    return record

def main():
    numRecords = int(sys.argv[1])
    gradesFp = open('grades-%d.txt'%numRecords, 'w')
    rollNos = [i for i in range(numRecords)]
    random.shuffle(rollNos)
    courses = [i for i in range(100)]
    courses = courses * (numRecords / 100)
    random.shuffle(courses)
    for rollNo, course in zip(rollNos, courses):
        grade = genGrade(100)
        record = genRecord(rollNo, course, grade)
        gradesFp.write(record+"\n")


if __name__ == "__main__":
    main()

