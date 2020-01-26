function W = read_graph(file_name)
fid=fopen(file_name);
n_str = fgetl(fid);
n = cell2mat(textscan( n_str, '%f', 'Delimiter',' ' ));
W = zeros(n);
        while 1
            tline = fgetl(fid);
            if ~ischar(tline), break, end
            a = cell2mat(textscan( tline, '%f', 'Delimiter',' ' ));
            W(a(1)+1,a(2)+1) = a(3);
            W(a(2)+1,a(1)+1) = a(3);
        end
fclose(fid);
end

