//////////////////////
// V1
// 19/11/2018
// Corentin Rasle
//////////////////////

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>

/////////////////////////////////////////////////////////////////////////////////
#define MAX_ORDER_INPUT 200
#define MAX_LINE_LENGTH 20
char input_filename [] = "input.txt";
char output_filename [] = 			"output.txt";


typedef struct
{
    int line;
    int col;
    int state_can;
} order;

typedef struct
{
    int prev_line;
    int prev_col;
    int current_col;
    int current_line;
    int next_col;
    int next_line;
    int can;
} state_robot;

order tab_order[MAX_ORDER_INPUT];
char final_order[MAX_ORDER_INPUT];
/////////////////////////////////////////////////////////////////////////////////////////////
int initialise_main_tables(void)
{
	for (int i=0; i<MAX_ORDER_INPUT; i++){
		tab_order[i].line = 50;
		tab_order[i].col = 50;
		tab_order[i].state_can='n';
		final_order[i]='a';
	}
	return 1;
}

/////////////////////////////////////////////////////////////////////////////////////////////

int load_order_file(char* filename)
{
    int nb_order=0;
    char *found2;
	char *string;
	char *found;
	char *found3;
	char line [MAX_LINE_LENGTH]; /* or other suitable maximum line size */
	order* oneorder;
    FILE* file_in = fopen(input_filename, "r");
	if (file_in != NULL) // verif fichier
    {
		printf("Loading measure file... ");
        // create new temp structure
        while (fgets ( line, sizeof line, file_in )!=NULL)
		{
            oneorder = (order*)malloc(sizeof(order));
            if (oneorder!=NULL){
                oneorder-> line = 0;
                oneorder-> col = 0;
                oneorder-> state_can = 'a';
            }
            else {
                printf ("Error\n");
            }
            // parse line to fill myTucky
            string = strdup(line);

            // Get first argument (date)
            found = strtok (string,";");
            found2=  strtok (NULL,";");
            found3= strtok(NULL,";");

            // Get second argument (temp)
            oneorder-> line = atoi(found);
            oneorder-> col = atoi(found2);
            oneorder-> state_can = atoi(found3);


            // copy structure
            tab_order[nb_order] = *oneorder;

            free(oneorder);
            nb_order++;
		}
        // close file
        fclose ( file_in );
        printf("OK\n");
    }

	else {
		printf("Error while opening %s\n", filename);
	}
    return nb_order;
}

int Affichage ( int nb_order)
{
    FILE* file_out = fopen(output_filename, "w");
	if (file_out != NULL) // verif fichier
    {
		for (int i=0; i<nb_order;i++){
			fprintf(file_out,"%c\t",final_order[i]);
		}
		fclose(file_out);
	}
	else {
		printf("Error while opening %s\n",output_filename);
	}
    return 1;
}

/////////////////////////////////////////////////////////////////////////////

int main (){

	int ret = 0;
	int nb_order=0;
	int nb_order_total=0;
	state_robot position;
    ret = initialise_main_tables();
	if (ret ==0) printf ("Erreur\n");

    nb_order= load_order_file(input_filename)	;
	if (nb_order ==0) printf ("Erreur 2 while loading %s\n", input_filename);
    // Print statistics
    printf("********************************\n");
    printf("nb order in file= %d \n",nb_order);
    printf("********************************\n\n");

    for (int i=1; i<nb_order-1; i++){
		position.can = tab_order[i+1].state_can;
		position.current_col = tab_order[i].col;
		position.current_line = tab_order[i].line;
		position.prev_col = tab_order[i-1].col;
		position.prev_line = tab_order[i-1].line;
		position.next_col = tab_order[i+1].col;
		position.next_line = tab_order[i+1].line;


        if (position.current_col==position.prev_col & position.current_line==(position.prev_line+1))
        {
            if (position.current_col==position.next_col & position.current_line==(position.next_line+1))
            {
                final_order[nb_order_total]='b';
                nb_order_total=nb_order_total+1;
            }
            else if (position.current_col==position.next_col & position.current_line==(position.next_line-1))
            {
                // don't turn
            }
            else if (position.current_col==(position.next_col+1) & position.current_line==(position.next_line))
            {
                final_order[nb_order_total]='r';
                nb_order_total=nb_order_total+1;
            }
            else if (position.current_col==(position.next_col-1) & position.current_line==(position.next_line))
            {
                final_order[nb_order_total]='l';
                nb_order_total=nb_order_total+1;
            }
        }
        else if (position.current_col==position.prev_col & position.current_line==(position.prev_line-1))
        {
            if (position.current_col==position.next_col & position.current_line==(position.next_line+1))
            {
               // don't turn
            }
            else if (position.current_col==position.next_col & position.current_line==(position.next_line-1))
            {
                final_order[nb_order_total]='b';
                nb_order_total=nb_order_total+1;
            }
            else if (position.current_col==(position.next_col+1) & position.current_line==(position.next_line))
            {
                final_order[nb_order_total]='l';
                nb_order_total=nb_order_total+1;
            }
            else if (position.current_col==(position.next_col-1) & position.current_line==(position.next_line))
            {
                final_order[nb_order_total]='r';
                nb_order_total=nb_order_total+1;
            }
        }
        else if (position.current_col==(position.prev_col+1) & position.current_line==position.prev_line)
        {
            if (position.current_col==position.next_col & position.current_line==(position.next_line+1))
            {
                final_order[nb_order_total]='l';
                nb_order_total=nb_order_total+1;
            }
            else if (position.current_col==position.next_col & position.current_line==(position.next_line-1))
            {
                final_order[nb_order_total]='r';
                nb_order_total=nb_order_total+1;
            }
            else if (position.current_col==(position.next_col+1) & position.current_line==(position.next_line))
            {
                final_order[nb_order_total]='b';
                nb_order_total=nb_order_total+1;
            }
            else if (position.current_col==(position.next_col-1) & position.current_line==(position.next_line))
            {
                // don't turn
            }
        }
        else if (position.current_col==(position.prev_col-1) & position.current_line==position.prev_line)
        {
            if (position.current_col==position.next_col & position.current_line==(position.next_line+1))
            {
                final_order[nb_order_total]='r';
                nb_order_total=nb_order_total+1;
            }
            else if (position.current_col==position.next_col & position.current_line==(position.next_line-1))
            {
                final_order[nb_order_total]='l';
                nb_order_total=nb_order_total+1;

            }
            else if (position.current_col==(position.next_col+1) & position.current_line==(position.next_line))
            {
                // don't turn
            }
            else if (position.current_col==(position.next_col-1) & position.current_line==(position.next_line))
            {
               final_order[nb_order_total]='b';
               nb_order_total=nb_order_total+1;
            }
        }

        if (position.can==1)
        {
            printf("pas de can\n\n");

            final_order[nb_order_total]='f';
            nb_order_total=nb_order_total+1;
        }
        if (position.can==0)
        {
            printf("on pousse la can\n\n");

            final_order[nb_order_total]='c';
            nb_order_total=nb_order_total+1;
        }

	}


#ifdef DEBUG_MAIN
#endif // DEBUG_MAIN

    ret= Affichage(nb_order);

    printf("end\n");
	return 0;
}
