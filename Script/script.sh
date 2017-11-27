awk -F"," ' FNR == 1 || $5=="Darebin (C)"' JourneyToWork_VISTA09_v3_VISTA_Online.csv > JTW_Darebin_start.csv
head -n-3  ABS-Census-2016-Darebin-Place-of-Work.csv | tail -n +4 | awk -F"," '{print $1}'> LGA_names.csv
awk -F "," 'NR==FNR {lga[$1]; next} $8 in lga {print > "Darebin (C)-"$8".csv"}' LGA_names.csv JTW_Darebin_start.csv
