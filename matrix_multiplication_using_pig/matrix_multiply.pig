/* 
  make sure you have properly installed hadoop & pig first before running this
  tutorial - https://www.tutorialspoint.com/apache_pig/apache_pig_pigstore.htm
*/
mat1 = LOAD 'mat1' USING PigStorage(',') AS (row,column,value:int);
mat2 = LOAD 'mat2' USING PigStorage(',') AS (row,column,value:int);

A = JOIN mat1 BY column FULL OUTER, mat2 BY row;
B = FOREACH A GENERATE mat1::row AS m1r, mat2::column AS m2c, (mat1::value)*(mat2::value) AS value;
C = GROUP B BY (m1r, m2c);

multiplied = FOREACH C GENERATE group.$0 as row, group.$1 as column, SUM(B.value) AS value;

STORE multiplied INTO 'product' USING PigStorage(',');
