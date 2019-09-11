require_relative "user_source_code0.rb"

def print_result 
  #pwd = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2/src/main/java/evaluatorSpringBoot/repository"
  pwd = "/app/src/main/java/evaluatorSpringBoot/repository/result.txt"

  10.times { |i| puts "Hello World" }

  File.open("/app/src/test/resources/new_ruby_file2.txt", "w") { |file| file.puts "Im stronger than steel!"}
  

  if test == nil 
    File.open(pwd, "w") { |file| file.puts test}
  else
    File.open(pwd, "w") { |file| file.puts test}
  end
end

print_result


