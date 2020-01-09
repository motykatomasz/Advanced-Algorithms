w = [0,1,0,3;1,0,0.5,2;0,0.5,0,1;3,2,1,0];
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
    r = normrnd(0,1,[1,4]);
    r = r./norm(r);

    S = r*B > 0;
    maxcut = sum(sum(w(find(S>0),find(S==0))));

    if maxcut > 0.87856*value(objective)
        break;
    end
end
