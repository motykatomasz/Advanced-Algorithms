w = read_graph("large.txt")
n=length(w);
yalmip('clear');
Y=sdpvar(n,n);
constraints=[Y>=0; diag(Y)==ones(n,1)];
objective=-trace(w*(ones(n,n)-Y))/4;
maxcutsol=solvesdp(constraints,objective);

[Q, A] = eig(value(Y));
B = Q * sqrt(A);
B = B';
while 1
    r = normrnd(0,1,[1,n]);
    r = r./norm(r);

    S = r*B > 0;
    candidate_sol = sum(sum(w(find(S>0),find(S==0))));

    if candidate_sol > 0.87856*value(objective)
        break;
    end
end

S
candidate_sol
