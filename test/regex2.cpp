#include <iostream>
#include <string>
#include <pcrecpp.h>

using namespace std;

int main(void) {
    int i;
    string s;
    pcrecpp::RE re("(\\w+):(\\d+)");
    if (re.error().length() > 0) {
        cout << "PCRE compilation failed with error: "
        << re.error() << "\n";
    }
    
    if (re.PartialMatch("root:1234", &s, &i)) {
        cout << s << ":" << i << "\n";
    }
}
