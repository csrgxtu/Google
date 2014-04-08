#!/usr/bin/python
#
# Author: Archer Reilly
# Date: 04/Apr/2014
# File: KeyWordRetrieve.py
# Des: this script is used to extract the keywords from the text file
# first, remove white spaces, then remove stopword, then remove
# unprintable characters, then to lowercase, then stemming.
#
# Produced By CSRGXTU

import sys

from nltk.stem.lancaster import LancasterStemmer

# loadStopWords
#
# @param stopwords.txt
# @return list
def loadStopWords(fileName):
  data = [line.strip() for line in open(fileName, 'r')]
  return data

# loadWords
# load words list from plain text file
#
# @param fileName
# @return list
def loadWords(fileName):
  data = [line.strip() for line in open(fileName, 'r')]
  return data

# removeStopWords
# Remove common words which has no search value
#
# @param stopWords
# @param data
# @return list
def removeStopWords(stopWords, data):
  return [word for word in data if word not in stopWords]

# stem
# stemming the words list
#
# @param list
# @return list
def stem(words):
  st = LancasterStemmer()
  return [st.stem(word) for word in words]

# removeDump
# remove dumplicate keys
#
# @param list
# @return list
def removeDump(words):
  set1 = set(words)
  list1 = list(set1)
  return list1

# svList2File
#
# @param list
# @param fileName
def svList2File(words, fileName):
  fh = open(fileName, "w")
  
  for item in words:
    fh.write(item + "\n")

  fh.close()


# main
# the main function that get all functions work together
#
# @param argv
def main(argv):
  # check the params
  if (len(argv) != 2):
    print "Usage: KeyWordRetrieve.py <stopWords.txt> <wordsList.txt>\n"
    sys.exit(1)
  stopWordFile = argv[0]
  wordListFile = argv[1]

  stopWords = loadStopWords(stopWordFile)
  wordList  = loadWords(wordListFile)
  keyWords  = removeStopWords(stopWords, wordList)
  keyWords  = stem(keyWords)
  keyWords  = removeDump(keyWords)
  
  svList2File(keyWords, wordListFile + ".keywords")
  print keyWords

   
# loadOriginalText
# load orginal text file into list
"""
stopWords = loadStopWords("../data/stopWordsFormated.txt")
data = loadWords("./fc71910dc2918115ebe8b0b5615a2959.html.content.txt.formated")
keyWords = removeStopWords(stopWords, data)
print keyWords
keywords = stem(keyWords)
print removeDump(keywords)
"""

if __name__ == "__main__":
  main(sys.argv[1:])
