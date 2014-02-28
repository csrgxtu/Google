/**
 * Author:
 * Date: 10/Jan/2014
 * File: StringToFile.cpp
 * Des: this file is used to save the string to a file that you specified.
 *
 * Produced By CSRGXTU
 **/
#include <string>
#include <iostream>
#include <fstream>
#include <unistd.h>
#include <stdlib.h>

class HTML2File {

private:
    // str to save the string 
	std::string str;
    //The filename
	//char* filename;
    //File saved path
	//char* path;
    //The file's absolute path
	std::string filepath;
    //String saved in failpath when occur error to save you specified file 
	std::string failpath;
public:
    /**constructor
     *@param1 string instr the contents to save
     *@param2 string pathstr the contents to save to
     *@param2 string failstr save the string  when failed to save the contents
     * into you specifed file, and it can be omit
     */
	HTML2File(std::string instr = NULL, std::string pathstr = NULL,
		std::string failstr = "./faildialog"){
		str = instr;
		failpath = failstr;
		//path = retrievepath(pathstr);
		//filename = retrievefilename(pathstr);
		filepath = pathstr;
	}
    // deal with the string save to the specified file
	void save() {
    // file have existed
		if(access(filepath.c_str(), F_OK) != -1) {  
    //judge whether the file can write, if cannot write to failpath
			if(access(filepath.c_str(), W_OK) == -1) {
				dealfail();
			}
			std::ofstream out;  
			out.open(filepath.c_str(), std::ofstream::out | std::ofstream::app);
 	//failed open file, save the contents to the failpath 
			if(!out.is_open()) {
				dealfail();
			}
			out << str;
			out.close();
		}
    
    //file not exist, create it
		else {
				std::ofstream out;
				out.open(filepath.c_str(), std::ofstream::out | std::ofstream::app);
				if(!out.is_open()) {
				 	dealfail();
				}
				out << str;
				out.close(); 
		}
	}
	
    //deal with error to save the contents to specified file
	void dealfail() {
		std::ofstream fout;
		fout.open(failpath.c_str());
		if(!fout.is_open()) {
             std::cout << "Failed to save the contents" << std::endl;
			 exit(1);
		}
		fout << str;
		fout.close();
	}
   // get failpath
	std::string getfailpath() const {
		return failpath;
	}
	const std::string getfilepath()const {
 	       return filepath;
	}
    //get contents you want to save
	const std::string getpassstr() const {
		return str;
	}
    //get the file name
	/*const std::string getfilename() const {
		return filename;
	}
    //get the file store path
	const std::string getpath() const {
		return path;
	}*/
    //set the file path
    void setfailpath(std::string failstr) {
        failpath = failstr;
    }
    //set the contents to save
    void setsavecontents(std::string savestr) {
        str = savestr;
    }
    //set the fail path
    void setfilepath(std::string pathstr) {
        filepath = pathstr;
    }
    void run() {
        save();
    }
    /* discard
    //retrieve the store path through path
	static char* retrievepath(std::string pathstring) {
		int count = 0;
		std::string::const_reverse_iterator start = pathstring.rbegin();
		while(*start != '/') {
            count++;
            ++start;
		}
		pathstring.erase(pathstring.end() - count - 1, pathstring.end());
		char* pathtemp = new char[pathstring.size() + 1];
		int i = 0;
		for(std::string::iterator iter = pathstring.begin();
                iter != pathstring.end(); ++iter)
            *(pathtemp + (i++)) = *iter;
		pathtemp[pathstring.size()] = '\0';
		return pathtemp;
	}
    //retrieve the file name through path
   static char* retrievefilename(std::string pathstring) {
		std::string temp1, temp2 = "";
        std::string::const_reverse_iterator start = pathstring.rbegin();
		while(*start != '/') {
			temp1 += *start;
			++start;
		}
		std::string::reverse_iterator riter = temp1.rbegin();
        for( ; riter != temp1.rend(); ++riter)
            temp2 += *riter;

        char* tempfilename = new char[temp2.size() + 1];
        int i = 0;
        for(std::string::iterator iter = temp2.begin();
                        iter != temp2.end(); ++iter)
            *(tempfilename + (i++)) = *iter;
        tempfilename[temp2.size()] = '\0';
		return tempfilename;
	}*/
	~HTML2File() {
		//delete []path;
		//delete []filename;
	}
};
