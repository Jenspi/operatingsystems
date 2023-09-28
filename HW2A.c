#include <stdio.h>    
#include <unistd.h>
int main(){
	//Receive user input and write output to file
	FILE *output = fopen("partA_output.csv", "w");
	printf("Enter fork() number:");
	int usrInput;
	scanf("%d", &usrInput);

	for(int i=1; i<usrInput+1; i++){
		fflush(output);
		fork();
	}
	
	//print all pid/ppids.. neds to be outside of loop otherwise we 
get 2x as many prints as supposed to
	fprintf(output, "[%d,%d]\n", getpid(), getppid());    
	return 0;   
} 
