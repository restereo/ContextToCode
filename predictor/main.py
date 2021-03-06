"""
main.py
"""
import tensorflow as tf
# from tasks.env.config
import time
import os

from tasks.generate_data import generate_addition, expect_to_prog
from tasks.eval import evaluate_addition, multiclass_eval
from tasks.train import train_addition
from tasks.data import data
from tasks.env.config import CONFIG


FLAGS = tf.app.flags.FLAGS
tf.app.flags.DEFINE_string("task", "addition", "Which NPI Task to run - [addition].")
tf.app.flags.DEFINE_boolean("generate", False, "Boolean whether to generate training/test data.")
tf.app.flags.DEFINE_boolean("expect_to_prog", False, "Boolean whether to generate training/test data.")
tf.app.flags.DEFINE_boolean("split", False, "Boolean whether to generate training/test data.")
tf.app.flags.DEFINE_integer("num_training", 2000, "Number of training examples to generate.")
tf.app.flags.DEFINE_integer("num_test", 600, "Number of test examples to generate.")

tf.app.flags.DEFINE_boolean("do_train", False, "Boolean whether to continue training model.")
tf.app.flags.DEFINE_boolean("do_eval", False, "Boolean whether to perform model evaluation.")
tf.app.flags.DEFINE_boolean("do_multiclass_eval", False, "Boolean whether to perform model evaluation.")
tf.app.flags.DEFINE_boolean("do_inference", False, "Boolean whether to perform model evaluation.")
tf.app.flags.DEFINE_boolean("data", False, "Boolean whether to perform model evaluation.")
tf.app.flags.DEFINE_integer("num_epochs",  1, "Number of training epochs to perform.")
tf.app.flags.DEFINE_string("sub", "", "Number of training epochs to perform.")
tf.app.flags.DEFINE_integer("start_epoch", 0, "Number of training epochs to perform.")
tf.app.flags.DEFINE_integer("start_step", 0, "Number of training epochs to perform.")


start_time = time.time()

# def split( ):
#     with open('/root/GeneratedScripts/db/yandex/clickhouse/traces/clickHouseQuery.json', 'r') as f:
#         content = f.readlines()
#     # you may also want to remove whitespace characters like `\n` at the end of each line
#     content = [x.strip() for x in content]
#     prog = [];
#     progs = [];
#     for c in content:
#         #if 'True' in c and prog:
#         progs.append(prog)
#         prog = []
#         prog.append(c)
#
#
#     print(len(progs))
#
#     count = 0;
#     for prog in progs:
#         with open('/root/GeneratedScripts/db/yandex/clickhouse/traces/data'+str(count), 'a') as f:
#             for c in prog:
#                 f.write(str(c))
#         count += 1;


def main(_):
    if FLAGS.task == "addition":
        # Generate Data (if necessary)
        if FLAGS.generate:
            for subdir, dirs, files in os.walk("/root/ContextToCode/data/datasets/classifiers"):
                for sub in dirs:
                    if not os.path.exists( "log/1class/"+sub ):				
                        generate_addition(sub)

        if FLAGS.expect_to_prog:
            count = 1
            for subdir, dirs, files in os.walk("/root/ContextToCode/predictor/log/1class/"):
                for sub in dirs:	
                    if not "!!" in sub:				
                        expect_to_prog(sub, count)
                        count += 1
            #generate_addition()

        # if FLAGS.split:
        #     split()

        # Train Model (if necessary)
        if FLAGS.do_train:
            if not os.path.exists( "log/"+FLAGS.sub.replace("log/", "").replace("/", "")+"/models/" ):
                train_addition(FLAGS.num_epochs, FLAGS.start_epoch, FLAGS.start_step, FLAGS.sub.replace("log/", "").replace("/", ""))
                print("--- %s seconds ---" % (time.time() - start_time))

        # Evaluate Model
        if FLAGS.do_eval:
            evaluate_addition()
        
        # Evaluate Model
        if FLAGS.do_multiclass_eval:
            multiclass_eval()
			
        # Evaluate Model
        if FLAGS.data:
            data()
			
        #if FLAGS.do_inference:
         #   inference()

if __name__ == "__main__":
    tf.app.run()
