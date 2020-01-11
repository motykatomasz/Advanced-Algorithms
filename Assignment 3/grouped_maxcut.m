clear;
clc;
format longG

path = "data/10/p1/"
file_names = get_files(path)
times = zeros(1,length(file_names));
upper_bounds = zeros(1,length(file_names));
lower_bounds = zeros(1,length(file_names));


for i = 1 : length(file_names)
    file_name = file_names(i);
    
    w = read_graph(path + file_name);
    tic
    n=length(w);
    yalmip('clear');
    Y=sdpvar(n,n);
    constraints=[Y>=0; diag(Y)==ones(n,1)];
    objective=-trace(w*(ones(n,n)-Y))/4;

    maxcutsol=solvesdp(constraints,objective);

    [Q, A] = eig(value(Y));
    B = Q * sqrt(A);
    B = B';
    times(1,i) = toc;
    upper_bounds(1,i) = -value(objective);
    
    while 1
        r = normrnd(0,1,[1,n]);
        r = r./norm(r);

        S = r*B > 0;
        candidate_sol = sum(sum(w(find(S>0),find(S==0))));

        if candidate_sol > 0.87856*(-value(objective))
            lower_bounds(1,i) = candidate_sol;
            break;
        end
    end
    
    

end
times
lower_bounds
upper_bounds