"""
eval.py

Loads in an Addition NPI, and starts a REPL for interactive addition.
"""
from model.npi import NPI
from tasks.generate_data import transform
from tasks.env.addition import AdditionCore
from tasks.env.config import CONFIG, get_args, LOG_PATH, DATA_PATH_TEST, CKPT_PATH, TEST_CHUNK_PATH, EVAL_LIMIT
from tasks.env.config import get_env
import numpy as np
import pickle
import tensorflow as tf
from tensorflow.python.platform import gfile
import json
import random
import time

def evaluate_addition():
    """
    Load NPI Model from Checkpoint, and initialize REPL, for interactive carry-addition.
    """
    with tf.Session() as sess:
        # Load Data
        with open(DATA_PATH_TEST, 'rb') as f:
            data = pickle.load(f)
        # Initialize Addition Core
        core = AdditionCore()

        # Initialize NPI Model
        npi = NPI(core, CONFIG, LOG_PATH)

        # Restore from Checkpoint
        saver = tf.train.Saver()
        saver.restore(sess, CKPT_PATH)

        # with gfile.FastGFile("/tmp/tf/log/graph.pb", 'rb') as f:
        #     graph_def = tf.GraphDef()
        #     graph_def.ParseFromString(f.read())
        #     sess.graph.as_default()
        #     tf.import_graph_def(graph_def)
        # print("map variables")

        # Run REPL

        predict = {};
        predict["ncw"] = 0;
        predict["ncr"] = 0;
        predict["cw"] = 0;
        predict["cr"] = 0;
        count = 0;

        #f = open('log/numbers.txt', 'r+')
        #f.truncate()

        limit = EVAL_LIMIT
        r = list(range(1000))
       # random.shuffle(r)
        for x in r:
            limit -= 1
            if limit > 0:
                res = ""
                print(repl(sess, npi, data, x, predict))
                print("predict_connect_right " + str(predict["cr"]) + " predict_connect_wrong " + str(predict["cw"]) + " predict_not_connect_right " + str(predict["ncr"]) + " predict_not_connect_wrong " + str(predict["ncw"]))
                print (str(limit)+"--------------------------")

def verbose (prog_out, prog_id, predict):
                if int(prog_out) > 1:
                    if int(prog_out) == int(prog_id):
                        predict["cr"] += 1;
                    else:
                        predict["cw"] += 1;
                else:
                    if int(prog_out) == int(prog_id):
                        predict["ncr"] += 1;
                    else:
                        predict["ncw"] += 1;

#                print ('%s y= Prog_id: %s' % (count, prog_id))
#                print ('%s y` = Prog_id: %s, Info: %s' % (count, prog_out, y[j]["addinfo"]))

#                count += 1
def multiclass_eval():
      with open(DATA_PATH_TEST, 'rb') as f:
          data = pickle.load(f)

      sess1 = tf.Session()
      sess2 = tf.Session()
      sess3 = tf.Session()

      # Initialize Addition Core
      core = AdditionCore()
      # Initialize NPI Model
      npi = NPI(core, CONFIG, LOG_PATH)

        # Restore from Checkpoint
      saver = tf.train.Saver()
    #  saver.restore(sess1, CKPT_PATH_CLASS1)
    #  saver.restore(sess2, CKPT_PATH_CLASS2)
    #  saver.restore(sess3, CKPT_PATH_CLASS3)

      predict = {};
      predict["ncw"] = 0;
      predict["ncr"] = 0;
      predict["cw"] = 0;
      predict["cr"] = 0;

      f = open('log/prog_produced.txt', 'w+')
      f.truncate()

      limit = EVAL_LIMIT
      r = list(range(1000))
      # random.shuffle(r)
      for x in r:
          limit -= 1
          if limit > 0:
            with open("/root/ContextToCode/predictor/log/prog_produced.txt", "a") as myfile:
                myfile.write(str(repl(sess1, npi, data, x, predict))+"\n")
                myfile.write(str(repl(sess2, npi, data, x, predict))+"\n")
                myfile.write(str(repl(sess3, npi, data, x, predict))+"\n")
            print (str(limit)+"--------------------------")

def repl(session, npi, data, pos, predict):
        start = time.time()
        steps = data[pos]
		
        # Reset NPI States
      #  npi.reset_state()
        programs = []

        x, y = steps[:-1], steps[1:]

        if len(x) > 0:
            for j in range(len(x)):

                prog_in, arg_in = [[x[j]["program"]["id"]]],  [get_args(x[j]["args"]["id"], arg_in=True)]
                prog_out, terminate_out = y[j]["program"]["id"],  y[j]["environment"]["terminate"]
                env_in = [get_env(x[j]["environment"])]

                t, n_p = session.run([npi.terminate, npi.program_distribution],
                                             feed_dict={npi.env_in: env_in, npi.arg_in:arg_in, npi.prg_in: prog_in})
											 
                graph = tf.get_default_graph()
#                for op in graph.get_operations():
#                    print(op.name)
#                terminate = graph.get_tensor_by_name('FullyConnected_3/BiasAdd:0')
#                program_distribution = graph.get_tensor_by_name('Sum:0')
#                env_in_ = graph.get_tensor_by_name('Env_Input:0')
#                arg_in_ = graph.get_tensor_by_name('Arg_Input:0')
#                prg_in_ = graph.get_tensor_by_name('Program_ID:0')

#                t, n_p = session.run([terminate, program_distribution], feed_dict={env_in_: env_in, arg_in_:arg_in, prg_in_: prog_in})

                programs.append(np.argmax(n_p))
                #if np.argmax(n_p) > 10:
                 #   sys.exit(1)

                #print ('fact %s predict %s' % (prog_out, np.argmax(n_p)))
                if predict:
                    verbose (prog_out, np.argmax(n_p), predict)
       
        end = time.time()
        print("--"+str(end - start)+" - "+str(len(graph.get_operations())))
        return programs

def repeat():
        lines = [line.rstrip('\n') for line in open("log/prog.txt")]

        for c in lines:
            prog_id, arg0, arg1 = map(int, c.rstrip('\n').split(","))

            if prog_id == MOVE_PID or prog_id == WRITE_PID:
                scratch.execute(prog_id, [arg0, arg1])
            # Print Environment
            scratch.pretty_print()