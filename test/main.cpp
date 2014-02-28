#include "HTML2File.cpp"
#include "WebPageDownload.cpp"
#include <iostream>
#include <string>

using namespace std;

int main() {
    string url = "http://blogxtu.zapto.org/";
    WebPageDownload page(url);
    page.run();
    //cout << page.getHeader() << endl;    

    string tmpstr = page.getHeaderContents();

    HTML2File fobj(tmpstr, "./maintest.html");
    fobj.run();
 
    return 0;
}
