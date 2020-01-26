clear;
clc;
w = read_graph("maxcut_10_05_100_instance_1.txt")
n=length(w);
yalmip('clear');
Y=sdpvar(n,n);
constraints=[Y>=0; diag(Y)==ones(n,1)];
objective=-trace(w*(ones(n,n)-Y))/4;
tic
maxcutsol=solvesdp(constraints,objective);
toc

tic
[Q, A] = eig(value(Y));
B = Q * sqrt(A);
B = B';
toc

while 1
    r = normrnd(0,1,[1,n]);
    r = r./norm(r);

    S = r*B > 0;
    final_sol = sum(sum(w(find(S>0),find(S==0))));

    if final_sol > 0.87856*(-value(objective))
        break;
    end
end
-value(objective)
final_sol
