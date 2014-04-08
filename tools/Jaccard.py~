#!/usr/bin/python
#
# Author: Archer Reilly
# Date: 08/Apr/2014
# File: Jaccard.py
# Des: this file contrains the code to calculate the Jaccard distance
# and similarity.
#
# Produced By CSRGXTU
import sys

# ldWords
# load keywords from file into list
#
# @param wordsFile
# @return set
def ldWords(wordsFile):
  words = [line.strip() for line in open(wordsFile, "r")]
  return set(words)

# jaccardDistance
# calculate the Jaccard distance about two sets
#
# @param set1
# @param set2
# @return dis
def jaccardDistance(set1, set2):
  union = set1.union(set2)
  intersection = set1.intersection(set2)
  
  return 1 - len(intersection) / len(union)


# jaccardSim
# calculate the Jaccard similarity of two sets
#
# @param set1
# @param set2
# @return sim
def jaccardSim(set1, set2):
  union = set1.union(set2)
  intersection = set1.intersection(set2)
  
  return len(intersection) / float(len(union))


# main
# main program that make all functions word together
#
# @param argv
def main(argv):
  if (len(argv) != 2):
    print "Usage: Jaccard.py <keyWordsList1> <keyWordsList2>"
    sys.exit(1)
  
  set1 = ldWords(argv[0])
  set2 = ldWords(argv[1])
  
  print jaccardSim(set1, set2)


if __name__ == "__main__":
  main(sys.argv[1:])
