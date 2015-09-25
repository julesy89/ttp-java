extern "C"
{
#include "combo.h"
}
#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm> 


struct IDgreater
{
    bool operator()( const item& lx, const item& rx ) const {
    	return lx.id < rx.id;
    }
};


int main(int argc, char* argv[]) {

     long maxWeight;
     std::cin >> maxWeight;

    long numOfItems;
    std::cin >> numOfItems;



    long p, w;
    std::vector<item> items;

    int counter = 0;
    while (std::cin >> p >> w)
    {
        item i;
        i.p = p;
        i.w = w;
        i.x = 0;
        i.id = counter++;
        items.push_back(i);
    }

     if (items.size() != numOfItems) {
         std::cout << "ERROR while reading items. Read " << items.size() << " exspected " << numOfItems<< "!\n";
         
         return EXIT_FAILURE;
     }

     item *f = &(*items.begin());
     item *l = &(*items.end());

   
     combo(f,l, maxWeight,0, 45654646464645, true, true);

     std::sort( items.begin(), items.end(), IDgreater() );     

     for(std::vector<item>::iterator it = items.begin(); it != items.end(); ++it) {
          //std::cout << it->id << " - " << it->p << " - " << it-> w << " - " << it->x << "\n";
          std::cout << it->x << "\n";
     }

}


