/**
 * Author: Archer Reilly
 * Date: 10/Jan/2014
 * File: WebPageDownloader.cpp
 * Des: this file is used to download a webpage according the url
 *  you specified. but not responsible for saving it to file, just
 *  get the requested page into memory, i guess in string format.
 *
 * Produced By CSRGXTU.
 **/
#include <stdio.h>
#include <curl/curl.h>
#include <cstdlib>
#include <string>
#include <iostream>
using namespace std;

class WebPageDownload {
    private:
        /**
         * USER_AGENT specifies the request client agent, some website need
         * client persent the user agent to access their contents
         **/
        string USER_AGENT;
        
        /**
         * url contains the url that will be requested
         **/
        string url;

        /**
         * header contains the header of the response of the reqeust.
         */
        string header;
        
        /**
         * contents contains the web page source code after the request
         **/
        string contents;
       
        /**
         * headerContents contains the refined header and the contents
         */
        string headerContents;
 
        /**
         * resCode contains the response code of the request
         **/
        int resCode;
        
        /**
         * curl is a pointer that holds the curl object
         **/
        CURL * curl;



    public:
        /**
         * constructor used to init some member vars and create a curl
         * handler.
         *
         * @param string url
         **/
        WebPageDownload(string url) {
            if (url.length() <= 9) {
                cout << "Fatal Error: constructor WebPageDownload " <<
                    "need url parameter!\n" << endl;
                exit(1);
            } else {
                this->url = url;
            }
            // default valule for member variables
            this->USER_AGENT = "Googlebot";
            this->contents = "";
            this->resCode = 403;
            this->curl = NULL;

            // set curl object and perform the request and put response into
            // the contents
            this->curl = curl_easy_init();
            if (!this->curl) {
                cout << "Fatal Error: can not init the curl!" << endl;
            }

            curl_easy_setopt(this->curl, CURLOPT_WRITEFUNCTION,
                &WebPageDownload::contentsCallBack);
            curl_easy_setopt(this->curl, CURLOPT_WRITEDATA, &contents);
            curl_easy_setopt(this->curl, CURLOPT_WRITEHEADER, &header);
        }
        
        /**
         * destructor is used to free the curl handler, for the libcurl need
         * the client program to do so.
         **/
        ~WebPageDownload() {
            curl_easy_cleanup(this->curl);
        }
        
        void setUrl(string url) {
            this->url = url;
        }

        void setUserAgent(string userAgent) {
            this->USER_AGENT = userAgent;
        }
       
        void setHeader(string header) {
            this->header = header;
        }
 
        void setContents(string contents) {
            this->contents = contents;
        }
        
        void setHeaderContents(string headerContents) {
            this->headerContents = headerContents;
        }

        void setResCode(int resCode) {
            this->resCode = resCode;
        }

        string getUserAgent() {
            return this->USER_AGENT;
        }
        
        string getUrl() {
            return this->url;
        }

        string getHeader() {
            return this->header;
        }

        string getContents() {
            return this->contents;
        }

        string getHeaderContents() {
            string tmpstr = "URL: ";
            tmpstr = tmpstr + this->url + "\r\n";
            tmpstr = tmpstr + this->header;
            tmpstr = tmpstr + this->contents;
            return tmpstr;
        }

        int getResCode() {
            return this->resCode;
        }

        /**
         * contentsCallBack is used to as a call back function for the curl 
         * library
         *
         * @param char *data
         * @param size_t size
         * @param size_t nmemb
         * @param string *buffer
         * @return int bytesread
         **/
        static int contentsCallBack(char *data, size_t size, size_t nmemb,
            string *buffer) {

            int result = 0;
            if (buffer != NULL) {
                buffer->append(data, size * nmemb);
                result = size * nmemb;
            }
            return result;
        }

        /**
         * run method is used to do the request with curl_easy_perform
         *
         **/
        bool run() {
            curl_easy_setopt(this->curl, CURLOPT_URL, this->url.c_str());
            curl_easy_setopt(this->curl, CURLOPT_USERAGENT,
                this->USER_AGENT.c_str());
            this->resCode = curl_easy_perform(this->curl);
        }
    
};

// testing
/*int main() {
    WebPageDownload page("http://blogxtu.zapto.org/");
    page.run();
    //cout << page.getHeader() << endl;
    //cout << page.getContents() << endl;
    cout << page.getHeaderContents() << endl;
    return 0;
}*/
