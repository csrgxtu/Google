#include <string>
#include <iostream>
#include <curl/curl.h>
 
class MyCurlObject {
  public:
    MyCurlObject (std::string url) {
      curl = curl_easy_init();
      if(!curl)
        throw std::string ("Curl did not initialize!");
 
      curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, &MyCurlObject::curlWriter);
      curl_easy_setopt(curl, CURLOPT_WRITEDATA, &curlBuffer);
      curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
      curl_easy_perform(curl);
    };
 
    static int curlWriter(char *data, size_t size, size_t nmemb, std::string *buffer) {
      int result = 0;
      if (buffer != NULL) {
        buffer->append(data, size * nmemb);
        result = size * nmemb;
      }
      return result;
    }
 
    std::string getData () { return curlBuffer; }
 
  protected:
    CURL * curl;
    std::string curlBuffer;
};
 
int main (int argc, char ** argv) {
  try {
    MyCurlObject mco ("http://www.google.com/");
    MyCurlObject moco ("http://www.yahoo.com/");
    std::cout << moco.getData() << std::endl;
    std::cout << "--------------------------------------------" << std::endl;
    std::cout << mco.getData() << std::endl;
  }
  catch (std::string & s) {
    std::cerr << "Oops! " << s << std::endl;
  }
}
