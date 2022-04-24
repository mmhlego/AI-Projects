#include <bits/stdc++.h>
using namespace std;

#define ll long long
#define pll pair<ll, ll>

void func(int a[], int n) {
	sort(a, a + 6);
}

int main() {
	ios::sync_with_stdio(false); cin.tie(0); cout.tie(0);
	int a[10] = { 1,3,2,4,5,6,7,5,9,10 };
	func(a, 10);

	for (int i = 0;i < 10;i++) {
		cout << a[i] << " ,";
	}
}